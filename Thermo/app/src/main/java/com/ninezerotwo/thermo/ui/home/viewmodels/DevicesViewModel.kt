package com.ninezerotwo.thermo.ui.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clj.fastble.data.BleDevice
import com.ninezerotwo.thermo.domain.models.Device
import com.ninezerotwo.thermo.domain.usecases.device.ConnectToDeviceUsecase
import com.ninezerotwo.thermo.domain.usecases.device.GetNearbyDevicesUsecase
import com.ninezerotwo.thermo.domain.utils.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val getNearbyDevicesUsecase: GetNearbyDevicesUsecase,
    private val connectToDeviceUsecase: ConnectToDeviceUsecase
): ViewModel() {

    init {
        scanDevices()
    }

    sealed class NearbyDevicesState{
        object Empty: NearbyDevicesState()
        object Failure: NearbyDevicesState()
        data class Success(val devices: List<Device>): NearbyDevicesState()
    }

    sealed class ConnectToDeviceState{
        object Empty: ConnectToDeviceState()
        object Failure: ConnectToDeviceState()
        object Success: ConnectToDeviceState()
    }

    private var _devicesStateLiveData = MutableLiveData<NearbyDevicesState>()
    val devicesStateLiveData: LiveData<NearbyDevicesState> get() = _devicesStateLiveData

    private var _deviceConnectStateLiveData = MutableLiveData<ConnectToDeviceState>()
    val deviceConnectStateLiveData: LiveData<ConnectToDeviceState> get() = _deviceConnectStateLiveData

   fun scanDevices(){
        getNearbyDevicesUsecase.invoke(viewModelScope,Unit){
            when(it){
                is Outcome.Failure -> {
                    _devicesStateLiveData.postValue(NearbyDevicesState.Failure)
                }
                is Outcome.Success -> {
                    _devicesStateLiveData.postValue(NearbyDevicesState.Success(it.data))
                }
            }
        }
    }

    fun connectToDevice(device: Device){
        connectToDeviceUsecase.invoke(viewModelScope,device){
            when(it){
                is Outcome.Failure -> _deviceConnectStateLiveData.postValue(
                    ConnectToDeviceState.Failure
                )
                is Outcome.Success -> _deviceConnectStateLiveData.postValue(
                    ConnectToDeviceState.Success
                )
            }
        }
    }

}