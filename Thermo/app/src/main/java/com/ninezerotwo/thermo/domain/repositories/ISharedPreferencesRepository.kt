package com.ninezerotwo.thermo.domain.repositories

interface ISharedPreferencesRepository {

    val DEFAULT_TOKEN: String
        get() = "tokenRepository"

    val DEFAULT_MAC: String
        get() = "DD:10:1C:97:66:71"

    fun getToken(): String

    fun saveToken(token: String)

    fun getMac(): String

    fun saveMac(mac: String)
}