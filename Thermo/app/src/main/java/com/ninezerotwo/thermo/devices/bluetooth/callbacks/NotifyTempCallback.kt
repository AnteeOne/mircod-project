package com.ninezerotwo.thermo.devices.bluetooth.callbacks

interface NotifyTempCallback {
    suspend fun getMacDevices(): String
    suspend fun setTempDevice(temp: Int)
}