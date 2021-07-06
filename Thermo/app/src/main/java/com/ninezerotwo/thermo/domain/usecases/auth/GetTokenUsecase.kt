package com.ninezerotwo.thermo.domain.usecases.auth

import com.ninezerotwo.thermo.domain.repositories.ITokenRepository
import com.ninezerotwo.thermo.domain.usecases.base.Usecase
import com.ninezerotwo.thermo.domain.utils.Outcome
import javax.inject.Inject

class GetTokenUsecase @Inject constructor(
    private val tokenRepository: ITokenRepository
) : Usecase<String,Unit>(){

    override suspend fun run(params: Unit): Outcome<String> {
        return try {
            val sharedToken = tokenRepository.getToken()
            Outcome.Success(sharedToken)
        } catch (ex: Exception){
            Outcome.Failure(ex)
        }
    }
}