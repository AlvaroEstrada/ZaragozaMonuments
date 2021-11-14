package com.alvaroestrada.zaragozamonuments.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.alvaroestrada.zaragozamonuments.R
import com.alvaroestrada.zaragozamonuments.core.extensions.isNotNull
import com.alvaroestrada.zaragozamonuments.core.extensions.toast
import com.alvaroestrada.zaragozamonuments.data.model.Monument
import com.alvaroestrada.zaragozamonuments.data.model.MonumentCluster
import com.alvaroestrada.zaragozamonuments.databinding.ActivityMainBinding
import com.alvaroestrada.zaragozamonuments.ui.listeners.OnMainActivityListener
import com.alvaroestrada.zaragozamonuments.ui.utils.MapStateManager
import com.alvaroestrada.zaragozamonuments.ui.view.fragments.MonumentsFragment
import com.alvaroestrada.zaragozamonuments.ui.view.fragments.MonumentsFragmentDetails
import com.alvaroestrada.zaragozamonuments.ui.viewmodel.MonumentsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import com.google.android.gms.maps.model.Marker

import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener





@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, OnMainActivityListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mActiveFragment: Fragment
    private lateinit var mFragmentManager: FragmentManager

    private lateinit var mMap: GoogleMap
    private val zaragozaCoordinates = LatLng(41.6584459,-0.8970703)
    private lateinit var mClusterManager: ClusterManager<MonumentCluster>

    private val REQUEST_CODE_LOCATION = 0
    private var monumentList = listOf<Monument>()

    var isAllFabsVisible: Boolean = false
    var markersVisible: Boolean = true

    private val monumentsViewModel: MonumentsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragments()
        monumentsViewModel.onCreate()
        setupMenuButtons()

        monumentsViewModel.monuments.observe(this, Observer { monuments ->
            monumentList = monuments
            if (::mMap.isInitialized){
                insertClusters(monumentList)
            }else{
                monumentsViewModel.onCreate()
            }
            //mMonumentsFragment.reloadRecyclerView(monuments)
        })

        monumentsViewModel.isRetrieveInfo.observe(this, Observer {
            binding.cvProgress.isVisible = it
        })

        setupMapIfNeeded()
    }

    private fun initFragments() {
        mFragmentManager = supportFragmentManager
        //mMonumentsFragment = MonumentsFragment(this)
        //mFragmentManager.beginTransaction().add(binding.fragmentContainer.id, mMonumentsFragment, MonumentsFragment::class.java.name).hide(mMonumentsFragment).commit()
    }

    private fun setupMenuButtons() {
        binding.menuMomuments.visibility = View.GONE
        binding.menuMonumentsText.visibility = View.GONE
        binding.menuShowMarkers.visibility = View.GONE
        binding.menuShowMarkersText.visibility = View.GONE
        binding.menuFab.shrink()

        binding.menuFab.setOnClickListener {
            if (!isAllFabsVisible){
                with(binding){
                    menuShowMarkers.show()
                    menuShowMarkersText.visibility = View.VISIBLE
                    menuMomuments.show()
                    menuMonumentsText.visibility = View.VISIBLE
                    menuFab.extend()
                    isAllFabsVisible = true
                }
            } else{
                with(binding){
                    menuShowMarkers.hide()
                    menuShowMarkersText.visibility = View.GONE
                    menuMomuments.hide()
                    menuMonumentsText.visibility = View.GONE
                    menuFab.shrink()
                    isAllFabsVisible = false
                }
            }
        }

        binding.menuShowMarkers.setOnClickListener {
            if (markersVisible){
                markersVisible = false
                mClusterManager.clearItems()
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMap.cameraPosition.target, mMap.cameraPosition.zoom -0.000001f), 1, null)
                binding.menuShowMarkers.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.menu_show_markers_icon))
                binding.menuShowMarkersText.text = getString(R.string.main_menu_show_markers_text)
            }else{
                markersVisible = true
                mClusterManager.addItems(parseMonumentsToClusterItem(monumentList))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMap.cameraPosition.target, mMap.cameraPosition.zoom -0.000001f), 1, null)
                binding.menuShowMarkersText.text = getString(R.string.main_menu_hide_markers_text)
                binding.menuShowMarkers.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.menu_hide_markers_icon))
            }
        }

        binding.menuMomuments.setOnClickListener {
            val dialogMonuments = MonumentsFragment(this, this, monumentList)
            dialogMonuments.show(supportFragmentManager, "Monuments")
            binding.menuFab.performClick()
        }

        binding.menuShowMarkersText.setOnClickListener { binding.menuShowMarkers.performClick() }
        binding.menuMonumentsText.setOnClickListener { binding.menuMomuments.performClick() }
    }

    // mFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).hide(mActiveFragment).show(mapFragment).commit()

    override fun onPause() {
        super.onPause()
        val manager = MapStateManager(this)
        manager.saveMapState(mMap)
        closeFragments()
    }

    private fun closeFragments() {
        val monuments = supportFragmentManager.findFragmentByTag("Monuments")
        val details = supportFragmentManager.findFragmentByTag("MonumentDetails")
        if (monuments != null) {
            val df: DialogFragment = monuments as DialogFragment
            df.dismiss()
        }
        if (details != null) {
            val df: DialogFragment = details as DialogFragment
            df.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        setupMapIfNeeded()
    }

    override fun locateMonument(monument: Monument) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(monument.geometry.coordinates[1], monument.geometry.coordinates[0]), 19f), 2000, null)
        closeFragments()
    }

    override fun detailsMonument(monument: Monument, locateVisible: Boolean) {
        val dialogMonuments = MonumentsFragmentDetails(this, monument, this, locateVisible)
        dialogMonuments.show(supportFragmentManager, "MonumentDetails")
    }

    private fun clearMap(){
        mMap.clear()
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }


    private fun insertClusters(monuments: List<Monument>){
        val monumentsCluster = parseMonumentsToClusterItem(monuments)
        setUpClusterer(monumentsCluster)
    }

    private fun moveMapToInitZone() {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(zaragozaCoordinates, 10f), 1000, null)
    }

    @SuppressLint("MissingPermission")
    private fun enableLocation(){
        if (!::mMap.isInitialized) return
        if (isLocationPermissionGranted()){
            mMap.isMyLocationEnabled = true
        }else{
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            toast(getString(R.string.request_permissions))
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mMap.isMyLocationEnabled = true
            }else{
                toast(getString(R.string.request_permissions))
            }
            else -> {}
        }
    }

    private fun setUpClusterer(monumentsCluster: List<MonumentCluster>) {
        if (!::mClusterManager.isInitialized){
            mClusterManager = ClusterManager(this, mMap)
            mMap.setOnCameraIdleListener(mClusterManager)
            mMap.setOnMarkerClickListener(mClusterManager)

            mClusterManager.setOnClusterItemClickListener {
                detailsMonument(it.getMonument()!!, false)
                true
            }

            mClusterManager.addItems(monumentsCluster)
            mClusterManager.cluster()
        }
    }

    private fun parseMonumentsToClusterItem(monuments: List<Monument>): MutableList<MonumentCluster>{
        val clusterItems = mutableListOf<MonumentCluster>()
        var index = 0
        monuments.forEach {
            if (it.geometry.isNotNull()){
                clusterItems.add(index, MonumentCluster(it.geometry.coordinates[1], it.geometry.coordinates[0], it.title, getString(R.string.cluster_more_info), it))
                index++
            }

        }
        return clusterItems
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED


    //-- Map setup methods
    private fun setupMapIfNeeded() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (!::mMap.isInitialized) {
            val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment!!.getMapAsync(this)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        enableLocation()
        moveMapToInitZone()

        val manager = MapStateManager(this)
        val position = manager.getSavedCameraPosition()
        if (position != null) {
            val update = CameraUpdateFactory.newCameraPosition(position)
            mMap.animateCamera(update, 1000, null)
            mMap.mapType = manager.getSavedMapType()
            mMap.uiSettings.isMapToolbarEnabled = true
        }
    }


}