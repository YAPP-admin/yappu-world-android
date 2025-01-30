package com.yapp.core.data.data.di

import com.yapp.core.data.data.repository.AuthorizedUserRepositoryImpl
import com.yapp.core.data.data.repository.UnAuthorizedUserRepositoryImpl
import com.yapp.dataapi.AuthorizedUserRepository
import com.yapp.dataapi.UnAuthorizedUserRepository
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
}
