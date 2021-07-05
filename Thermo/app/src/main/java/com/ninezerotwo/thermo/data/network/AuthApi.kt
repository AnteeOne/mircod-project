package com.ninezerotwo.thermo.data.network

import com.ninezerotwo.thermo.data.network.dto.UserSignInResponse
import com.ninezerotwo.thermo.data.network.dto.UserSignInDto
import com.ninezerotwo.thermo.data.network.dto.UserSignUpDto
import com.ninezerotwo.thermo.data.network.dto.UserSignUpResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @POST("registration/")
    suspend fun signUp(@Body dto: UserSignUpDto): UserSignUpResponse

    @POST("login/")
    suspend fun signIn(@Body dto: UserSignInDto): UserSignInResponse

    @POST("logout/")
    suspend fun signOut(@Header(value = "Authorization") userToken: String): Unit
}
