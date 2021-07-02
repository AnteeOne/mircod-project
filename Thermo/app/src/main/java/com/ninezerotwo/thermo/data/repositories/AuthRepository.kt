package com.ninezerotwo.thermo.data.repositories

import com.ninezerotwo.thermo.domain.models.User
import com.ninezerotwo.thermo.domain.repositories.IAuthRepository

class AuthRepository : IAuthRepository {
    override suspend fun signIn(): String {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(): User {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }
}
