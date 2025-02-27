package com.yapp.dataapi

import com.yapp.model.UserInfo

interface UserProfileRepository {
    suspend fun getUserProfile() : UserInfo
}