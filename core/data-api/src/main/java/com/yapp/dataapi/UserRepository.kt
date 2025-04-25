package com.yapp.dataapi

import com.yapp.model.ActivityHistory
import com.yapp.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserAccessToken() : Flow<String>
    suspend fun deleteAccount()
    suspend fun getUserProfile(): Flow<UserInfo>
    fun getUserActivityHistories(): Flow<ActivityHistory>
}