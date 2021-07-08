package com.ninezerotwo.thermo.devices.bluetooth.callbacks

import com.ninezerotwo.thermo.ui.home.entity.DeviceDto

interface NotifyTempCallback {
    suspend fun getDevices(list: MutableList<DeviceDto>)
    suspend fun getMacDevices(): String
    suspend fun setTempDevice(temp: Int)
}