package com.ninezerotwo.thermo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ninezerotwo.thermo.data.db.dao.TemperatureDao
import com.ninezerotwo.thermo.domain.models.Temperature

@Database(entities = [Temperature::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun temperatureDao(): TemperatureDao

    companion object {

        private const val DATABASE_NAME = "temperature.db"

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}