package com.ninezerotwo.thermo.data.network.dto


import com.google.gson.annotations.SerializedName

data class UserSignInDto(
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)