package com.ninezerotwo.thermo.domain.repositories

interface IThermoRepository {

    suspend fun connectToDevice(deviceMac: String): Boolean

}