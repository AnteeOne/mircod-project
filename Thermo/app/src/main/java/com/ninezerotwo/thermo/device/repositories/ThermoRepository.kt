package com.ninezerotwo.thermo.device.repositories

import android.bluetooth.BluetoothGatt
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleGattCallback
import com.clj.fastble.callback.BleNotifyCallback
import com.clj.fastble.callback.BleReadCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.exception.BleException
import com.ninezerotwo.thermo.device.bluetooth.BleConstants
import com.ninezerotwo.thermo.device.bluetooth.ByteConverter
import com.ninezerotwo.thermo.domain.repositories.IThermoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class ThermoRepository @Inject constructor(
    private val manager: BleManager
) : IThermoRepository {

    private var savedBleDevice: BleDevice? = null

    override suspend fun connectToDevice(deviceMac: String): Boolean =
        suspendCancellableCoroutine { cor ->
            manager.connect(deviceMac, object : BleGattCallback() {
                override fun onStartConnect() {}

                override fun onConnectFail(bleDevice: BleDevice?, exception: BleException?) {
                    cor.resumeWith(Result.failure(IllegalStateException(exception?.description)))
                }

                override fun onConnectSuccess(
                    bleDevice: BleDevice?,
                    gatt: BluetoothGatt?,
                    status: Int
                ) {
                    savedBleDevice = bleDevice
                    cor.resumeWith(Result.success(true))
                }

                override fun onDisConnected(
                    isActiveDisConnected: Boolean,
                    device: BleDevice?,
                    gatt: BluetoothGatt?,
                    status: Int
                ) {
                    savedBleDevice = null
                }

            })
        }

    override suspend fun getBatteryLevel(): Int = suspendCancellableCoroutine { cor ->
        manager.read(savedBleDevice,
            BleConstants.BATTERY_SERVICE_UUID,
            BleConstants.BATTERY_CHAR_UUID,
            object : BleReadCallback() {

                override fun onReadSuccess(data: ByteArray) {
                    cor.resumeWith(Result.success(data[0].toInt()))
                }

                override fun onReadFailure(exception: BleException) {
                    cor.resumeWith(Result.failure(IllegalStateException(exception.description)))
                }

            })
    }

    @ExperimentalCoroutinesApi
    override fun getTemperature(): Flow<Float> = callbackFlow {
        manager.notify(savedBleDevice,
            BleConstants.TEMP_SERVICE_UUID,
            BleConstants.TEMP_CHAR_UUID,
            object : BleNotifyCallback() {
                override fun onNotifySuccess() {}

                override fun onNotifyFailure(exception: BleException) {
                    close()
                }

                override fun onCharacteristicChanged(data: ByteArray) {
                    trySend(ByteConverter.toTemperature(data[0], data[1])).onFailure {
                        close()
                    }
                }

            })
        awaitClose {
            cancel()
        }

    }


}