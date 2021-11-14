package com.alvaroestrada.zaragozamonuments.domain

import com.alvaroestrada.zaragozamonuments.data.model.Monument
import com.alvaroestrada.zaragozamonuments.data.repository.MonumentsRepository
import javax.inject.Inject

/**********
 * Projecto: Zaragoza Monuments
 * Desde: com.alvaroestrada.zaragozamonuments.domain
 * Creado por Álvaro Estrada en 13/11/2021
 * Todos los derechos reservados 2021
 **********/

class GetMonumentsUseCase @Inject constructor(private val repository: MonumentsRepository) {

    suspend operator fun invoke(): List<Monument> = repository.getAllMonuments()

}