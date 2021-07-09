package com.ninezerotwo.thermo.domain.repositories

import com.ninezerotwo.thermo.domain.models.Temperature

interface IDatabaseRepository {
    suspend fun addTemperature(temp: Temperature)
    suspend fun getTemperature(): List<Temperature>
}