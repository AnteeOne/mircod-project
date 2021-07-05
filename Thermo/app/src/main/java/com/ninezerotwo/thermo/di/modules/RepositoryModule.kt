package com.ninezerotwo.thermo.di.modules

import com.ninezerotwo.thermo.data.network.AuthApi
import com.ninezerotwo.thermo.data.repositories.AuthRepository
import com.ninezerotwo.thermo.domain.repositories.IAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(authApi: AuthApi): IAuthRepository =
        AuthRepository(authApi)

}