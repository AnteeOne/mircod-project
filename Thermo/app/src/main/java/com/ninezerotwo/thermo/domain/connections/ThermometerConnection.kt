package com.ninezerotwo.thermo.domain.connections

import com.ninezerotwo.thermo.devices.bluetooth.callbacks.NotifyTempCallback
import com.ninezerotwo.thermo.ui.home.entity.DeviceDto

interface ThermometerConnection {
    suspend fun searchDevices(): MutableList<DeviceDto>
    suspend fun connectToDevice(notifyTempCallback: NotifyTempCallback)
    suspend fun discoverBatteryValue(): Int

}