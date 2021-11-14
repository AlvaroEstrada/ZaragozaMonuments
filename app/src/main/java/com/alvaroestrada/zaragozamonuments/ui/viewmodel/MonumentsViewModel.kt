package com.alvaroestrada.zaragozamonuments.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvaroestrada.zaragozamonuments.data.model.Monument
import com.alvaroestrada.zaragozamonuments.domain.GetMonumentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**********
 * Projecto: Zaragoza Monuments
 * Desde: com.alvaroestrada.zaragozamonuments.ui.viewmodel
 * Creado por Álvaro Estrada en 12/11/2021
 **********/

@HiltViewModel
class MonumentsViewModel @Inject constructor(
    private val getMonumentsUseCase: GetMonumentsUseCase
) : ViewModel() {

    val monuments = MutableLiveData<List<Monument>>()
    val isRetrieveInfo = MutableLiveData<Boolean>()

    fun onCreate() {
        viewModelScope.launch {
            isRetrieveInfo.postValue(true)
            val result = getMonumentsUseCase()

            if (!result.isNullOrEmpty()){
                monuments.postValue(result)
                isRetrieveInfo.postValue(false)
            }
        }
    }
}