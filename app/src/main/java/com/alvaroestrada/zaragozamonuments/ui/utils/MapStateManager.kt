package com.alvaroestrada.zaragozamonuments.ui.utils
import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import android.content.SharedPreferences


/**********
 * Projecto: Zaragoza Monuments
 * Desde: com.alvaroestrada.zaragozamonuments.ui.utils
 * Creado por Álvaro Estrada en 14/11/2021
 * Todos los derechos reservados 2021
 **********/

class MapStateManager(context: Context) {

    private val LONGITUDE = "longitude"
    private val LATITUDE = "latitude"
    private val ZOOM = "zoom"
    private val BEARING = "bearing"
    private val TILT = "tilt"
    private val MAPTYPE = "MAPTYPE"

    private val PREFS_NAME = "mapCameraState"

    private var mapStatePrefs: SharedPreferences? = null

    init {
        mapStatePrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveMapState(mapMie: GoogleMap) {
        val editor = mapStatePrefs!!.edit()
        val position = mapMie.cameraPosition
        editor.putFloat(LATITUDE, position.target.latitude.toFloat())
        editor.putFloat(LONGITUDE, position.target.longitude.toFloat())
        editor.putFloat(ZOOM, position.zoom)
        editor.putFloat(TILT, position.tilt)
        editor.putFloat(BEARING, position.bearing)
        editor.putInt(MAPTYPE, mapMie.mapType)
        editor.commit()
    }

    fun getSavedCameraPosition(): CameraPosition? {
        val latitude = mapStatePrefs!!.getFloat(LATITUDE, 0f).toDouble()
        if (latitude == 0.0) {
            return null
        }
        val longitude = mapStatePrefs!!.getFloat(LONGITUDE, 0f).toDouble()
        val target = LatLng(latitude, longitude)
        val zoom = mapStatePrefs!!.getFloat(ZOOM, 0f)
        val bearing = mapStatePrefs!!.getFloat(BEARING, 0f)
        val tilt = mapStatePrefs!!.getFloat(TILT, 0f)
        return CameraPosition(target, zoom, tilt, bearing)
    }

    fun getSavedMapType(): Int {
        return mapStatePrefs!!.getInt(MAPTYPE, GoogleMap.MAP_TYPE_NORMAL)
    }
}