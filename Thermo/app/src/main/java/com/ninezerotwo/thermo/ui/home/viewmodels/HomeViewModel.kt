package com.ninezerotwo.thermo.ui.home.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ninezerotwo.thermo.domain.usecases.device.SynchroniseDeviceUsecase
import com.ninezerotwo.thermo.domain.utils.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.subscribe
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val synchroniseDeviceUsecase: SynchroniseDeviceUsecase
): ViewModel() {

    fun synchroniseDevice(){
        synchroniseDeviceUsecase.invoke(viewModelScope,Unit){
            when(it){
                is Outcome.Failure -> Log.d("apptag","Synchronise with device: Failure")
                is Outcome.Success -> {
                    Log.d("apptag","Synchronise with device: Success")
                    Log.d("apptag","Battery: ${it.data.first}%")
                    it.data.second?.onEach {
                        Log.d("apptag","Temperature: ${it}")
                    }
                }
            }
        }
    }
}