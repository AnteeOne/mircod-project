package com.ninezerotwo.thermo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ninezerotwo.thermo.domain.models.Temperature


@Dao
interface TemperatureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(temperature: Temperature)

    @Query("SELECT * FROM temperature")
    fun getAllTemperatures(): List<Temperature>
}