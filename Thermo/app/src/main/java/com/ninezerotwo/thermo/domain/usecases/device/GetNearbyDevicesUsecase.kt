package com.ninezerotwo.thermo.domain.usecases.device

import android.util.Log
import com.ninezerotwo.thermo.domain.models.Device
import com.ninezerotwo.thermo.domain.repositories.IDevicesRepository
import com.ninezerotwo.thermo.domain.usecases.base.Usecase
import com.ninezerotwo.thermo.domain.utils.Outcome
import javax.inject.Inject

class GetNearbyDevicesUsecase @Inject constructor(
    private val devicesRepository: IDevicesRepository
): Usecase<List<Device>, Unit>() {

    override suspend fun run(params: Unit): Outcome<List<Device>> {
        try {
            val devices = devicesRepository.getNearbyDevice()
            return Outcome.Success(devices)
        }
        catch (ex: Exception){
            Log.d("apptag",ex.message.toString())
            return Outcome.Failure(ex)
        }
    }
}