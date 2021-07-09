package com.ninezerotwo.thermo.ui.home.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ninezerotwo.thermo.domain.usecases.device.SynchroniseDeviceUsecase
import com.ninezerotwo.thermo.domain.utils.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val synchroniseDeviceUsecase: SynchroniseDeviceUsecase
): ViewModel() {

    init {
        synchroniseDevice()
    }

    sealed class SynchState{
        object Empty: SynchState()
        object Failure: SynchState()
        object Success: SynchState()
    }

    sealed class BatteryState{
        object Empty: BatteryState()
        object Failure: BatteryState()
        data class Success(val battery: Int): BatteryState()
    }

    sealed class TemperatureState{
        object Empty: TemperatureState()
        object Failure: TemperatureState()
        data class Success(val temperature: Float): TemperatureState()
    }

    private var _synchStateLiveData = MutableLiveData<SynchState>()
    val synchStateLiveData: LiveData<SynchState> get() = _synchStateLiveData

    private var _batteryStateLiveData = MutableLiveData<BatteryState>()
    val batteryStateLiveData: LiveData<BatteryState> get() = _batteryStateLiveData

    private var _temperatureStateLiveData = MutableLiveData<TemperatureState>()
    val temperatureStateLiveData: LiveData<TemperatureState> get() = _temperatureStateLiveData

    fun synchroniseDevice(){
        synchroniseDeviceUsecase.invoke(viewModelScope,Unit){
            when(it){
                is Outcome.Failure -> Log.d("apptag","Synchronise with device: Failure")
                is Outcome.Success -> {
                    Log.d("apptag","Synchronise with device: Success")
                    Log.d("apptag","Battery: ${it.data.first}%")
                    _synchStateLiveData.value = SynchState.Success
                    _batteryStateLiveData.value = BatteryState.Success(it.data.first ?: 0)
//                    viewModelScope.launch {
//                        it.data.second?.collect{item ->
//                            _temperatureStateLiveData.value = TemperatureState.Success(item)
//                        }
//                    }
                }
            }
        }
    }
}