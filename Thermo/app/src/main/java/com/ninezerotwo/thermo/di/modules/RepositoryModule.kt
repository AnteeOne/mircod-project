package com.ninezerotwo.thermo.di.modules

import android.content.Context
import com.ninezerotwo.thermo.data.network.AuthApi
import com.ninezerotwo.thermo.data.repositories.AuthRepository
import com.ninezerotwo.thermo.data.repositories.TokenRepository
import com.ninezerotwo.thermo.domain.repositories.IAuthRepository
import com.ninezerotwo.thermo.domain.repositories.ITokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(authApi: AuthApi): IAuthRepository =
        AuthRepository(authApi)

    @Singleton
    @Provides
    fun provideTokenRepository(@ApplicationContext context: Context): ITokenRepository =
        TokenRepository(context)

}