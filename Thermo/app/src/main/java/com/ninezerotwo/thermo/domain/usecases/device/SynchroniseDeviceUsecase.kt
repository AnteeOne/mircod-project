package com.ninezerotwo.thermo.domain.usecases.device

import android.util.Log
import com.ninezerotwo.thermo.domain.models.Container
import com.ninezerotwo.thermo.domain.repositories.ISharedPreferencesRepository
import com.ninezerotwo.thermo.domain.repositories.IThermoRepository
import com.ninezerotwo.thermo.domain.usecases.base.Usecase
import com.ninezerotwo.thermo.domain.utils.Outcome
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SynchroniseDeviceUsecase @Inject constructor(
    private val thermoRepository: IThermoRepository,
    private val sharedRepository: ISharedPreferencesRepository
) : Usecase<Container<Int, Flow<Float>>, Unit>() {

    override suspend fun run(params: Unit): Outcome<Container<Int, Flow<Float>>> {
        try {
            val resultContainer = Container<Int,Flow<Float>>()
            val deviceMac = sharedRepository.getMac()
            if(deviceMac == sharedRepository.DEFAULT_MAC){
                return Outcome.Failure(IllegalStateException())
            }
            else{
                val isConnected = thermoRepository.connectToDevice(deviceMac)
                if(isConnected){
                    resultContainer.first = thermoRepository.getBatteryLevel()
                    resultContainer.second = thermoRepository.getTemperature()
                    return Outcome.Success(resultContainer)
                }
                return Outcome.Failure(IllegalStateException())
            }
        }
        catch (ex: Exception){
            return Outcome.Failure(ex)
        }
    }
}