package com.yapp.core.data.data.di

import com.yapp.core.data.data.repository.AlarmRepositoryImpl
import com.yapp.core.data.data.repository.UserRepositoryImpl
import com.yapp.core.data.data.repository.ConfigRepositoryImpl
import com.yapp.core.data.data.repository.AuthRepositoryImpl
import com.yapp.dataapi.AlarmRepository
import com.yapp.dataapi.UserRepository
import com.yapp.dataapi.ConfigRepository
import com.yapp.dataapi.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepositoryImpl(
        repositoryImpl: AuthRepositoryImpl,
    ): AuthRepository

    @Binds
    abstract fun bindUserRepositoryImpl(
        repositoryImpl: UserRepositoryImpl,
    ): UserRepository

    @Binds
    abstract fun bindConfigRepositoryImpl(
        repositoryImpl: ConfigRepositoryImpl,
    ): ConfigRepository

    @Binds
    abstract fun bindAlarmRepositoryImpl(
        repositoryImpl: AlarmRepositoryImpl,
    ): AlarmRepository
}
