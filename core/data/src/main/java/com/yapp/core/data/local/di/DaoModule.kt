package com.yapp.core.data.local.di

import com.yapp.core.data.local.RoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Singleton
    @Provides
    fun providePositionDao(db: RoomDataBase) = db.positionConfigDao()
}
