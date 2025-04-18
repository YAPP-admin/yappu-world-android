package com.yapp.core.data.remote.di

import com.yapp.core.data.remote.api.AlarmApi
import com.yapp.core.data.remote.api.AttendanceApi
import com.yapp.core.data.remote.api.AuthApi
import com.yapp.core.data.remote.api.OperationsApi
import com.yapp.core.data.remote.api.PostsApi
import com.yapp.core.data.remote.api.SessionApi
import com.yapp.core.data.remote.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Singleton
    @Provides
    fun provideUnAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOperationsApi(retrofit: Retrofit): OperationsApi {
        return retrofit.create(OperationsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAlarmApi(@AuthRetrofit retrofit: Retrofit): AlarmApi {
        return retrofit.create(AlarmApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUserApi(@AuthRetrofit retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun providePostsApi(retrofit: Retrofit): PostsApi {
        return retrofit.create(PostsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAttendanceApi(@AuthRetrofit retrofit: Retrofit): AttendanceApi {
        return retrofit.create()
    }

    @Singleton
    @Provides
    fun providesSessionsApi(@AuthRetrofit retrofit: Retrofit) = retrofit.create<SessionApi>()
}
