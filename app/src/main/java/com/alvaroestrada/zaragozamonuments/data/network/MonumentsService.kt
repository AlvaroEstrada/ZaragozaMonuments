package com.alvaroestrada.zaragozamonuments.data.network

import com.alvaroestrada.zaragozamonuments.data.model.MonumentResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**********
 * Projecto: Zaragoza Monuments
 * Desde: com.alvaroestrada.zaragozamonuments.data.network
 * Creado por Álvaro Estrada en 13/11/2021
 * Todos los derechos reservados 2021
 **********/

class MonumentsService @Inject constructor(private val api: MonumentsApiClient) {

    suspend fun getMonuments(): MonumentResponse{
        return withContext(Dispatchers.IO){
            val response = api.getAllMonuments()
            response.body()!!
        }
    }
}