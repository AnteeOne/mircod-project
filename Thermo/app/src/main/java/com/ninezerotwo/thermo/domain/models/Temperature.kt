package com.ninezerotwo.thermo.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Temperature(
    @ColumnInfo(name = "value") val value: Float,
    @ColumnInfo(name = "date") val date: Float
)
