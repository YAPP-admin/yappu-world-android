package com.yapp.core.data.remote.di

import com.yapp.core.data.remote.api.UnAuthorizedUserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): UnAuthorizedUserApi {
        return retrofit.create(UnAuthorizedUserApi::class.java)
    }
}
