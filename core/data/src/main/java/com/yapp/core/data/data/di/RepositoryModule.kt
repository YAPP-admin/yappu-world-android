package com.yapp.core.data.data.di

import com.yapp.core.data.data.repository.AuthorizedUserRepositoryImpl
import com.yapp.core.data.data.repository.ConfigRepositoryImpl
import com.yapp.core.data.data.repository.LoginRepositoryImpl
import com.yapp.core.data.data.repository.UnAuthorizedUserRepositoryImpl
import com.yapp.core.data.data.repository.UserProfileRepositoryImpl
import com.yapp.dataapi.AuthorizedUserRepository
import com.yapp.dataapi.ConfigRepository
import com.yapp.dataapi.LoginRepository
import com.yapp.dataapi.UnAuthorizedUserRepository
import com.yapp.dataapi.UserProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindUnAuthorizedUserRepositoryImpl(
        repositoryImpl: UnAuthorizedUserRepositoryImpl,
    ): UnAuthorizedUserRepository

    @Binds
    abstract fun bindAuthorizedUserRepositoryImpl(
        repositoryImpl: AuthorizedUserRepositoryImpl,
    ): AuthorizedUserRepository

    @Binds
    abstract fun bindConfigRepositoryImpl(
        repositoryImpl: ConfigRepositoryImpl,
    ): ConfigRepository

    @Binds
    abstract fun bindLoginRepositoryImpl(
        repositoryImpl: LoginRepositoryImpl,
    ): LoginRepository

    @Binds
    abstract fun bindUserProfileRepositoryImpl(
        repositoryImpl: UserProfileRepositoryImpl
    ): UserProfileRepository
}
