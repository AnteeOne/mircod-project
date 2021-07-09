package com.ninezerotwo.thermo.di.modules

import android.content.Context
import com.clj.fastble.BleManager
import com.ninezerotwo.thermo.ui.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BluetoothModule {

    @Provides
    @Singleton
    fun provideBleManager(@ApplicationContext appContext: Context): BleManager =
        BleManager.getInstance().apply {
            init(appContext as App)
            enableLog(true)
                .setReConnectCount(1, 5000)
                .setSplitWriteNum(20)
                .setConnectOverTime(10000)
                .setOperateTimeout(10000)
        }

}