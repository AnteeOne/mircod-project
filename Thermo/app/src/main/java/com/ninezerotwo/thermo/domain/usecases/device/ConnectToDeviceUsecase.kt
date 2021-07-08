package com.ninezerotwo.thermo.domain.usecases.device

import android.util.Log
import com.ninezerotwo.thermo.domain.models.Device
import com.ninezerotwo.thermo.domain.repositories.ISharedPreferencesRepository
import com.ninezerotwo.thermo.domain.repositories.IThermoRepository
import com.ninezerotwo.thermo.domain.usecases.base.Usecase
import com.ninezerotwo.thermo.domain.utils.Outcome
import javax.inject.Inject

class ConnectToDeviceUsecase @Inject constructor(
    private val thermoRepository: IThermoRepository,
    private val sharedPreferencesRepository: ISharedPreferencesRepository
): Usecase<Boolean,Device>() {

    override suspend fun run(params: Device): Outcome<Boolean> {
        return try {
            val isConnected = thermoRepository.connectToDevice(params.mac)
            if (isConnected) {
                sharedPreferencesRepository.saveMac(params.mac)
                Outcome.Success(true)
            }
            else Outcome.Failure(IllegalStateException("Connection failure!"))
        } catch (ex: Exception){
            Log.d("apptag","Connection Failure: ${ex.message}")
            Outcome.Failure(ex)
        }
    }
}