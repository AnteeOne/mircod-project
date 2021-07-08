package com.ninezerotwo.thermo.domain.usecases.auth

import com.ninezerotwo.thermo.domain.repositories.ISharedPreferencesRepository
import com.ninezerotwo.thermo.domain.usecases.base.Usecase
import com.ninezerotwo.thermo.domain.utils.Outcome
import java.lang.IllegalStateException
import javax.inject.Inject

class GetTokenUsecase @Inject constructor(
    private val sharedPreferencesRepository: ISharedPreferencesRepository
) : Usecase<String,Unit>(){

    override suspend fun run(params: Unit): Outcome<String> {
        return try {
            val sharedToken = sharedPreferencesRepository.getToken()
            if(sharedToken != sharedPreferencesRepository.DEFAULT_TOKEN) Outcome.Success(sharedToken)
            else Outcome.Failure(IllegalStateException())
        } catch (ex: Exception){
            Outcome.Failure(ex)
        }
    }
}