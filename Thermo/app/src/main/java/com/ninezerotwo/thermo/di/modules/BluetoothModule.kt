package com.ninezerotwo.thermo.di.modules

import android.app.Application
import com.clj.fastble.BleManager
import com.ninezerotwo.thermo.devices.bluetooth.BluetoothConnect
import com.ninezerotwo.thermo.domain.connections.ThermometerConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class BluetoothModule {

    @Singleton
    @Provides
    fun provideBleManager(app: Application): BleManager = BleManager.getInstance().also {
        it.init(app)
    }

    @Singleton
    @Provides
    fun provideContextBluetooth(app: Application): CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())


    @Singleton
    @Provides
    fun provideThermometerConnection(bleManager: BleManager, scope: CoroutineScope):
            ThermometerConnection = BluetoothConnect(bleManager, scope)
}