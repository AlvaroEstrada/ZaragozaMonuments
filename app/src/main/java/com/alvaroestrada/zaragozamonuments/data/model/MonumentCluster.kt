package com.alvaroestrada.zaragozamonuments.data.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

/**********
 * Projecto: Zaragoza Monuments
 * Desde: com.alvaroestrada.zaragozamonuments.data.model
 * Creado por Álvaro Estrada en 13/11/2021
 * Todos los derechos reservados 2021
 **********/

class MonumentCluster(lat: Double, lng: Double, title: String, snippet: String, monument: Monument) : ClusterItem{

    private val position: LatLng
    private val title: String
    private val snippet: String
    private val monument: Monument

    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String? {
        return title
    }

    override fun getSnippet(): String? {
        return snippet
    }

    fun getMonument(): Monument? {
        return monument
    }

    init {
        position = LatLng(lat, lng)
        this.title = title
        this.snippet = snippet
        this.monument = monument
    }
}