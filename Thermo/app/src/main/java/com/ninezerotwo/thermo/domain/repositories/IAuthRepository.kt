package com.ninezerotwo.thermo.domain.repositories

import com.ninezerotwo.thermo.domain.models.User

interface IAuthRepository {

    suspend fun signIn(): User

    suspend fun signUp(): User

    suspend fun signOut(): Unit
}
