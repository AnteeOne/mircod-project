package com.ninezerotwo.thermo.domain.usecases.auth

import android.util.Log
import com.ninezerotwo.thermo.domain.models.User
import com.ninezerotwo.thermo.domain.repositories.IAuthRepository
import com.ninezerotwo.thermo.domain.usecases.base.Usecase
import com.ninezerotwo.thermo.domain.utils.Outcome
import javax.inject.Inject

class SignInUsecase @Inject constructor(
    val authRepository: IAuthRepository
) : Usecase<String, User>() {

    override suspend fun run(params: User): Outcome<String> {
        return try {
            val userToken = authRepository.signIn(params)
            Outcome.Success(userToken)
        } catch (ex: Exception){
            Log.e("apptag",ex.toString())
            Outcome.Failure(ex)
        }
    }

}