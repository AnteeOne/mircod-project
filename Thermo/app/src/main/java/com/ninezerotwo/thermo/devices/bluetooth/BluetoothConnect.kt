package com.ninezerotwo.thermo.devices.bluetooth

import android.bluetooth.BluetoothGatt
import android.util.Log
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleGattCallback
import com.clj.fastble.callback.BleNotifyCallback
import com.clj.fastble.callback.BleScanCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.exception.BleException
import com.clj.fastble.scan.BleScanRuleConfig
import com.ninezerotwo.thermo.devices.bluetooth.callbacks.NotifyTempCallback
import com.ninezerotwo.thermo.devices.bluetooth.utils.Constants.TEMP_CHAR_UUID
import com.ninezerotwo.thermo.devices.bluetooth.utils.Constants.TEMP_SERVICE_UUID
import com.ninezerotwo.thermo.devices.bluetooth.utils.Convert
import com.ninezerotwo.thermo.domain.connections.ThermometerConnection
import com.ninezerotwo.thermo.ui.home.entity.DeviceDto
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class BluetoothConnect @Inject constructor(
    private val manager: BleManager,
    //private val notifyTempCallback: NotifyTempCallback,
    private val bluetoothScope: CoroutineScope,
): ThermometerConnection {
    private var devices: MutableList<DeviceDto> =  ArrayList()

    init {
        manager.also {
            manager.enableLog(true)
                .setReConnectCount(1, 5000)
                .setSplitWriteNum(20)
                .setConnectOverTime(10000000)
                .setOperateTimeout(5000);

            val scanRuleConfig = BleScanRuleConfig.Builder()
                .setAutoConnect(false)
                //.setDeviceMac("DD:10:1C:97:66:71")
                .setScanTimeOut(5000)
                .build()
            manager.initScanRule(scanRuleConfig)
        }
    }

    override suspend fun searchDevices(): MutableList<DeviceDto> {
        manager.scan(object : BleScanCallback(){
               override fun onScanStarted(success: Boolean) {
                   Log.d("apptag", "scan started!!!")
               }
               override fun onScanning(bleDevice: BleDevice?) {
                   Log.d("apptag", "${bleDevice?.name} - ${bleDevice?.mac ?: ""}")
               }
               override fun onScanFinished(scanResultList: MutableList<BleDevice>?) {
                   if (scanResultList != null) {
                       Log.d("apptag", "devices: ${scanResultList?.toString() ?: ""}")
                       for (bleDevice in scanResultList){
                           devices.add(DeviceDto(bleDevice?.name ?: "Empty", bleDevice.mac))
                       }
                   }
                   Log.d("apptag", "scan finished!!!")
               }
           })
            Log.d("apptag", "${devices.toString() } @@@@@@@@@@@@@@@@@@@@@@@@@@")
            return devices

    }

    override suspend fun connectToDevice(notifyTempCallback: NotifyTempCallback){
        manager.connect(notifyTempCallback.getMacDevices(), object : BleGattCallback(){
            override fun onStartConnect() {
                Log.d("apptag", "Start connnect!")
            }

            override fun onConnectFail(bleDevice: BleDevice?, exception: BleException?) {
                Log.d("apptag", "Fail!  " + (exception?.description ?: "Empty"))
            }

            override fun onConnectSuccess(
                bleDevice: BleDevice?,
                gatt: BluetoothGatt?,
                status: Int
            ) {
                // TODO: 08.07.2021 test it as soon as possible!
                bluetoothScope.launch{
                    notifyTempCallback.setTempDevice(getTempNotification(bleDevice!!))
                }
                Log.d("apptag", "Connect!")
                Log.d("apptag", bleDevice?.device?.address?.toString()?: "empty")
            }

            override fun onDisConnected(
                isActiveDisConnected: Boolean,
                device: BleDevice?,
                gatt: BluetoothGatt?,
                status: Int
            ) {
                Log.d("apptag", "Disconnect notify temp!")
            }
        })
    }

    override suspend fun discoverBatteryValue(): Int {
        TODO("Not yet implemented")
    }

    private fun getTempNotification(bleDevice: BleDevice): Int{
        var currentTemp = 0
        manager.notify(bleDevice, TEMP_SERVICE_UUID, TEMP_CHAR_UUID,object : BleNotifyCallback(){
            override fun onNotifySuccess() {
                Log.d("apptag","notify temp success")
            }

            override fun onNotifyFailure(exception: BleException?) {
                Log.d("apptag","notify temp failure")
            }

            override fun onCharacteristicChanged(data: ByteArray?) {
                var x = data!!
                currentTemp = Integer.parseInt(Convert.toBinary(data[0].toInt()) +
                        Convert.toBinary(data[1].toInt()), 2)

                Log.d("apptag","notification temp = $currentTemp")
            }

        })

        return currentTemp
    }
}