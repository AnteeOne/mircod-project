package com.ninezerotwo.thermo.data.network

import com.ninezerotwo.thermo.data.network.dto.UserResponseDto
import com.ninezerotwo.thermo.data.network.dto.UserSignInDto
import com.ninezerotwo.thermo.data.network.dto.UserSignUpDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("registration")
    suspend fun signUp(@Body dto: UserSignUpDto): UserResponseDto

    @POST("login")
    suspend fun signIn(@Body dto: UserSignInDto): UserResponseDto
}
