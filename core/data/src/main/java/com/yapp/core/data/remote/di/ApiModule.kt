package com.yapp.core.data.remote.di

import com.yapp.core.data.remote.api.ConfigApi
import com.yapp.core.data.remote.api.LoginApi
import com.yapp.core.data.remote.api.UnAuthorizedUserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Singleton
    @Provides
    fun provideUnAuthorizedUserApi(retrofit: Retrofit): UnAuthorizedUserApi {
        return retrofit.create(UnAuthorizedUserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideConfigApi(retrofit: Retrofit): ConfigApi {
        return retrofit.create(ConfigApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

}
