package com.ninezerotwo.thermo.data.repositories

import android.content.Context
import android.content.SharedPreferences
import com.ninezerotwo.thermo.domain.repositories.ITokenRepository
import javax.inject.Inject

class TokenRepository @Inject constructor(
    val context: Context
) : ITokenRepository {

    private val SHARED_NAME = "tokenRepository"
    private val SHARED_KEY = "accessToken"

    private val sharedPreferences: SharedPreferences? =
        context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)

    override fun getToken(): String {
        val token: String? = sharedPreferences?.getString(SHARED_KEY, null)
        return token ?: DEFAULT_TOKEN
    }

    override fun saveToken(token: String) {
        sharedPreferences
            ?.edit()
            ?.putString(SHARED_KEY, token)
            ?.apply()
    }
}