package com.ninezerotwo.thermo.domain.repositories

interface ITokenRepository {

    val DEFAULT_TOKEN: String
        get() = "tokenRepository"

    fun getToken(): String

    fun saveToken(token: String)
}