package com.alvaroestrada.zaragozamonuments.ui.listeners

import android.view.View
import com.alvaroestrada.zaragozamonuments.data.model.Monument

/**********
 * Projecto: Zaragoza Monuments
 * Desde: com.alvaroestrada.zaragozamonuments.ui.listeners
 * Creado por Álvaro Estrada en 13/11/2021
 * Todos los derechos reservados 2021
 **********/
interface OnClickItemListener {
    fun locateItem(monument: Monument)
    fun detailsItem(monument: Monument, locateVisible: Boolean)
}