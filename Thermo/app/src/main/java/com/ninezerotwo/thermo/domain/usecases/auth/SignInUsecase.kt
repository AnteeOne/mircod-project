package com.ninezerotwo.thermo.domain.usecases.auth

import android.util.Log
import com.ninezerotwo.thermo.domain.models.User
import com.ninezerotwo.thermo.domain.repositories.IAuthRepository
import com.ninezerotwo.thermo.domain.repositories.ISharedPreferencesRepository
import com.ninezerotwo.thermo.domain.usecases.base.Usecase
import com.ninezerotwo.thermo.domain.utils.Outcome
import javax.inject.Inject

class SignInUsecase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val sharedPreferencesRepository: ISharedPreferencesRepository
) : Usecase<String, User>() {

    override suspend fun run(params: User): Outcome<String> {
        return try {
            val userToken = authRepository.signIn(params)
            sharedPreferencesRepository.saveToken(userToken)
            Outcome.Success(userToken)
        } catch (ex: Exception){
            Log.e("apptag",ex.toString())
            Outcome.Failure(ex)
        }
    }

}