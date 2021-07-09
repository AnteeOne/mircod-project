package com.ninezerotwo.thermo.data.repositories

import com.ninezerotwo.thermo.data.db.AppDatabase
import com.ninezerotwo.thermo.domain.models.Temperature
import com.ninezerotwo.thermo.domain.repositories.IDatabaseRepository
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    private val database: AppDatabase
) : IDatabaseRepository {
    override suspend fun addTemperature(temp: Temperature) {
        database.temperatureDao().insert(temp)
    }

    override suspend fun getTemperature(): List<Temperature> {
        return database.temperatureDao().getAllTemperatures()
    }
}