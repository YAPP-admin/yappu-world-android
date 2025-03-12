package com.yapp.dataapi

import com.yapp.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserAccessToken() : String
    suspend fun deleteAccount()
    suspend fun getUserProfile(): Flow<UserInfo>
}