package com.ninezerotwo.thermo.domain.usecases.auth

import android.util.Log
import com.ninezerotwo.thermo.domain.models.User
import com.ninezerotwo.thermo.domain.repositories.IAuthRepository
import com.ninezerotwo.thermo.domain.usecases.base.Usecase
import com.ninezerotwo.thermo.domain.utils.Outcome
import javax.inject.Inject

class SignUpUsecase @Inject constructor(
    private val authRepository: IAuthRepository
) : Usecase<User, User>() {

    override suspend fun run(params: User): Outcome<User> {
        return try {
            val responseUser = authRepository.signUp(params)
            Outcome.Success(responseUser)
        } catch (ex: Exception){
            Log.e("apptag",ex.toString())
            Outcome.Failure(ex)
        }
    }

}