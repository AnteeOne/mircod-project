package com.ninezerotwo.thermo.data.repositories

import android.content.Context
import android.content.SharedPreferences
import com.ninezerotwo.thermo.domain.repositories.ISharedPreferencesRepository
import javax.inject.Inject

class SharedPreferencesRepository @Inject constructor(
    val context: Context
) : ISharedPreferencesRepository {

    private val SHARED_NAME = "tokenRepository"
    private val TOKEN_KEY = "accessToken"
    private val MAC_KEY = "savedMac"

    private val sharedPreferences: SharedPreferences? =
        context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)

    override fun getToken(): String {
        val token: String? = sharedPreferences?.getString(TOKEN_KEY, null)
        return token ?: DEFAULT_TOKEN
    }

    override fun saveToken(token: String) {
        sharedPreferences
            ?.edit()
            ?.putString(TOKEN_KEY, token)
            ?.apply()
    }

    override fun getMac(): String {
        val token: String? = sharedPreferences?.getString(MAC_KEY, null)
        return token ?: DEFAULT_MAC
    }

    override fun saveMac(mac: String) {
        sharedPreferences
            ?.edit()
            ?.putString(MAC_KEY, mac)
            ?.apply()
    }


}