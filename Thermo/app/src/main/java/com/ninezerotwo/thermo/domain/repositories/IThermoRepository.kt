package com.ninezerotwo.thermo.domain.repositories

import kotlinx.coroutines.flow.Flow

interface IThermoRepository {

    suspend fun connectToDevice(deviceMac: String): Boolean

    suspend fun getBatteryLevel(): Int

    fun getTemperature(): Flow<Float>

}