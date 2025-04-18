package com.yapp.core.data.data.di

import com.yapp.core.data.data.repository.AlarmRepositoryImpl
import com.yapp.core.data.data.repository.AttendanceRepositoryImpl
import com.yapp.core.data.data.repository.AuthRepositoryImpl
import com.yapp.core.data.data.repository.OperationsRepositoryImpl
import com.yapp.core.data.data.repository.PostsRepositoryImpl
import com.yapp.core.data.data.repository.SessionsRepositoryImpl
import com.yapp.core.data.data.repository.UserRepositoryImpl
import com.yapp.dataapi.AlarmRepository
import com.yapp.dataapi.AttendanceRepository
import com.yapp.dataapi.AuthRepository
import com.yapp.dataapi.OperationsRepository
import com.yapp.dataapi.PostsRepository
import com.yapp.dataapi.SessionRepository
import com.yapp.dataapi.UserRepository
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
    abstract fun bindOperationsRepositoryImpl(
        repositoryImpl: OperationsRepositoryImpl,
    ): OperationsRepository

    @Binds
    abstract fun bindAlarmRepositoryImpl(
        repositoryImpl: AlarmRepositoryImpl,
    ): AlarmRepository

    @Binds
    abstract fun bindPostsRepositoryImpl(
        repositoryImpl: PostsRepositoryImpl,
    ): PostsRepository

    @Binds
    abstract fun bindsAttendanceRepositoryImpl(
        repositoryImpl: AttendanceRepositoryImpl
    ): AttendanceRepository

    @Binds
    abstract fun bindsSessionRepositoryImpl(
        repositoryImpl: SessionsRepositoryImpl
    ): SessionRepository
}
