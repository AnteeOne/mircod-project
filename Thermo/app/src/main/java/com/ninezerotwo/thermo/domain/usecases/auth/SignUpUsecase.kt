package com.ninezerotwo.thermo.domain.usecases.auth

import com.ninezerotwo.thermo.domain.models.User
import com.ninezerotwo.thermo.domain.repositories.IAuthRepository
import com.ninezerotwo.thermo.domain.usecases.base.Usecase
import com.ninezerotwo.thermo.domain.utils.Outcome
import javax.inject.Inject

class SignUpUsecase @Inject constructor(
    val authRepository: IAuthRepository
) : Usecase<User, User>() {

    override suspend fun run(params: User): Outcome<User> {
        return try {
            val responseUser = authRepository.signUp(params)
            Outcome.Success(responseUser)
        } catch (ex: Exception){
            Outcome.Failure(ex)
        }
    }

}