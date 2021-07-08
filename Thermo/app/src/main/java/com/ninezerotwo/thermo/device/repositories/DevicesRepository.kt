package com.ninezerotwo.thermo.device.repositories

import android.util.Log
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleScanCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.scan.BleScanRuleConfig
import com.ninezerotwo.thermo.domain.models.Device
import com.ninezerotwo.thermo.domain.repositories.IDevicesRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class DevicesRepository @Inject constructor(
    private val manager: BleManager
) : IDevicesRepository {

    val scanRuleConfig = BleScanRuleConfig.Builder()
        .setAutoConnect(false)
//        .setDeviceMac("DD:10:1C:97:66:71")
        .setScanTimeOut(5000)
        .build()

    override suspend fun getNearbyDevice(): List<Device> = suspendCancellableCoroutine { cor ->
        manager.initScanRule(scanRuleConfig)
        manager.scan(object : BleScanCallback() {
            override fun onScanStarted(success: Boolean) {}

            override fun onScanning(bleDevice: BleDevice) {}

            override fun onScanFinished(scanResultList: MutableList<BleDevice?>) {
                scanResultList.let {
                    cor.resumeWith(Result.success(it.map { bleDevice ->
                        Device(bleDevice?.name ?: "Device", bleDevice?.mac ?: "mac")
                    }))
                }
                Log.d("apptag", "devices: ${scanResultList?.toString() ?: ""}")
            }

        })
    }

}