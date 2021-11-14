package com.alvaroestrada.zaragozamonuments.data.model

import javax.inject.Inject
import javax.inject.Singleton

/**********
 * Projecto: Zaragoza Monuments
 * Desde: com.alvaroestrada.zaragozamonuments.data.model
 * Creado por Álvaro Estrada en 12/11/2021
 **********/

@Singleton
class MonumentsProvider @Inject constructor(){
    var monuments: List<Monument> = emptyList()
}