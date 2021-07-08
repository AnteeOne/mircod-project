package com.ninezerotwo.thermo.di.modules

import android.content.Context
import com.clj.fastble.BleManager
import com.ninezerotwo.thermo.data.network.AuthApi
import com.ninezerotwo.thermo.data.repositories.AuthRepository
import com.ninezerotwo.thermo.data.repositories.SharedPreferencesRepository
import com.ninezerotwo.thermo.device.repositories.DevicesRepository
import com.ninezerotwo.thermo.device.repositories.ThermoRepository
import com.ninezerotwo.thermo.domain.repositories.IAuthRepository
import com.ninezerotwo.thermo.domain.repositories.IDevicesRepository
import com.ninezerotwo.thermo.domain.repositories.IThermoRepository
import com.ninezerotwo.thermo.domain.repositories.ISharedPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(authApi: AuthApi): IAuthRepository =
        AuthRepository(authApi)

    @Singleton
    @Provides
    fun provideTokenRepository(@ApplicationContext context: Context): ISharedPreferencesRepository =
        SharedPreferencesRepository(context)

    @Singleton
    @Provides
    fun provideDevicesRepository(bleManager: BleManager): IDevicesRepository =
        DevicesRepository(bleManager)

    @Singleton
    @Provides
    fun providesThermoRepository(bleManager: BleManager): IThermoRepository =
        ThermoRepository(bleManager)

}