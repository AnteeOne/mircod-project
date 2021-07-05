package com.ninezerotwo.thermo.domain.repositories

import com.ninezerotwo.thermo.domain.models.User

interface IAuthRepository {

    suspend fun signIn(user: User): String

    suspend fun signUp(user: User): User

    suspend fun signOut(): Unit
}
