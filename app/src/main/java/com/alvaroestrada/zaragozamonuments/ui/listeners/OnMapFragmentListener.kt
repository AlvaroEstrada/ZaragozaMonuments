package com.alvaroestrada.zaragozamonuments.ui.listeners

import com.alvaroestrada.zaragozamonuments.data.model.Monument

/**********
 * Projecto: Zaragoza Monuments
 * Desde: com.alvaroestrada.zaragozamonuments.ui.listeners
 * Creado por Álvaro Estrada en 13/11/2021
 * Todos los derechos reservados 2021
 **********/
interface OnMapFragmentListener {

    fun clearMap()

    fun insertMarker(monument: Monument)

    fun markMonument(monument: Monument)
    fun insertMarkers(monuments: List<Monument>)
    fun insertClusters(monuments: List<Monument>)
}