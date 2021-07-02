package com.ninezerotwo.thermo.data.network.mappers

import com.ninezerotwo.thermo.data.network.dto.UserResponseDto
import com.ninezerotwo.thermo.data.network.dto.UserSignInDto
import com.ninezerotwo.thermo.data.network.dto.UserSignUpDto
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

    fun toUser(userResponseDto: UserResponseDto) = User(
        username = userResponseDto.data.username,
        email = userResponseDto.data.email,
        firstName = userResponseDto.data.firstName,
        lastName = userResponseDto.data.lastName,
    )
}
