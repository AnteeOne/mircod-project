package com.ninezerotwo.thermo.data.repositories

import com.ninezerotwo.thermo.data.network.AuthApi
import com.ninezerotwo.thermo.data.network.mappers.UserApiMapper
import com.ninezerotwo.thermo.domain.models.User
import com.ninezerotwo.thermo.domain.repositories.IAuthRepository
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) : IAuthRepository {

    override suspend fun signIn(user: User): String {
        val userResponse = authApi.signIn(
            UserApiMapper.toUserSignInDto(user)
        )
        return userResponse.token
    }

    override suspend fun signUp(user: User): User {
       val userResponse = authApi.signUp(
           UserApiMapper.toUserSignUpDto(user)
       )
       return UserApiMapper.toUser(userResponse)
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }

    //TODO:Rewrite with exceptions
}
