package com.alvaroestrada.zaragozamonuments.data.repository

import com.alvaroestrada.zaragozamonuments.data.model.Monument
import com.alvaroestrada.zaragozamonuments.data.model.MonumentsProvider
import com.alvaroestrada.zaragozamonuments.data.network.MonumentsService
import javax.inject.Inject

/**********
 * Projecto: Zaragoza Monuments
 * Desde: com.alvaroestrada.zaragozamonuments.data.repository
 * Creado por Álvaro Estrada en 13/11/2021
 * Todos los derechos reservados 2021
 **********/

class MonumentsRepository @Inject constructor(private val api: MonumentsService, private val monumentsProvider: MonumentsProvider) {

    suspend fun getAllMonuments(): List<Monument>{
        val response = api.getMonuments()
        monumentsProvider.monuments = response.monuments
        return response.monuments
    }
}