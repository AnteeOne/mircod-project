package com.ninezerotwo.thermo.domain.repositories

import com.ninezerotwo.thermo.domain.models.Device

interface IDevicesRepository {

    suspend fun getNearbyDevice(): List<Device>

}