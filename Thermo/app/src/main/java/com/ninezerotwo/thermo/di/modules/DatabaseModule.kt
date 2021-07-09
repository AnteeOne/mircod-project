package com.ninezerotwo.thermo.di.modules

import android.content.Context
import com.ninezerotwo.thermo.data.db.AppDatabase
import com.ninezerotwo.thermo.ui.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase = AppDatabase(appContext as App)
}