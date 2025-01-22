package com.yapp.core.data.data.di

import com.yapp.core.data.data.repository.UnAuthorizedUserRepositoryImpl
import com.yapp.data_api.UnAuthorizedUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindTokenRepository(
        tokenRepositoryImpl: UnAuthorizedUserRepositoryImpl,
    ): UnAuthorizedUserRepository
}
