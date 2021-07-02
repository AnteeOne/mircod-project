package com.ninezerotwo.thermo.data.network.mappers

import com.ninezerotwo.thermo.data.network.dto.UserSignInResponse
import com.ninezerotwo.thermo.data.network.dto.UserSignInDto
import com.ninezerotwo.thermo.data.network.dto.UserSignUpDto
import com.ninezerotwo.thermo.data.network.dto.UserSignUpResponse
import com.ninezerotwo.thermo.domain.models.User

object UserApiMapper {

    fun toUserSignInDto(user: User) = UserSignInDto(
        password = user.password,
        username = user.username
    )

    fun toUserSignUpDto(user: User) = UserSignUpDto(
        email = user.email,
        firstName = user.firstName,
        lastName = user.lastName,
        password = user.password,
        username = user.username
    )

    fun toUser(userSignInResponse: UserSignInResponse) = User(
        username = userSignInResponse.data.username,
        email = userSignInResponse.data.email,
        firstName = userSignInResponse.data.firstName,
        lastName = userSignInResponse.data.lastName,
    )

    fun toUser(userSignUpResponse: UserSignUpResponse) = User(
        username = userSignUpResponse.username,
        email = userSignUpResponse.email,
        firstName = userSignUpResponse.firstName,
        lastName = userSignUpResponse.lastName,

    )
}
