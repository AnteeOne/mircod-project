package com.ninezerotwo.thermo.device.repositories

import android.bluetooth.BluetoothGatt
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleGattCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.exception.BleException
import com.ninezerotwo.thermo.domain.repositories.IThermoRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class ThermoRepository @Inject constructor(
    private val manager: BleManager
): IThermoRepository {

    override suspend fun connectToDevice(deviceMac: String): Boolean = suspendCancellableCoroutine { cor ->
        manager.connect(deviceMac,object: BleGattCallback(){
            override fun onStartConnect() {}

            override fun onConnectFail(bleDevice: BleDevice?, exception: BleException?) {
                cor.resumeWith(Result.failure(IllegalStateException(exception?.description)))
            }

            override fun onConnectSuccess(
                bleDevice: BleDevice?,
                gatt: BluetoothGatt?,
                status: Int
            ) {
                cor.resumeWith(Result.success(true))
            }

            override fun onDisConnected(
                isActiveDisConnected: Boolean,
                device: BleDevice?,
                gatt: BluetoothGatt?,
                status: Int
            ) {}

        })
    }
}