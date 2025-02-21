package com.yapp.dataapi

import com.yapp.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    suspend fun getUserProfile() : Flow<UserInfo>
}