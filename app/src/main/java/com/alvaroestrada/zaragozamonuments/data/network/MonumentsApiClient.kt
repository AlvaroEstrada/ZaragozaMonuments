package com.alvaroestrada.zaragozamonuments.data.network

import com.alvaroestrada.zaragozamonuments.data.model.MonumentResponse
import retrofit2.Response
import retrofit2.http.GET

/**********
 * Projecto: Zaragoza Monuments
 * Desde: com.alvaroestrada.zaragozamonuments.data.network
 * Creado por Álvaro Estrada en 13/11/2021
 **********/
interface MonumentsApiClient {
    @GET("monumento.json?fl=title%2Chorario%2Cgeometry%2Cimage%2Cdescription&rf=html&srsname=wgs84&start=0&rows=200&locale=es")
    suspend fun getAllMonuments(): Response<MonumentResponse>

}