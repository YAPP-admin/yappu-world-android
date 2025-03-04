package com.yapp.dataapi

import com.yapp.model.UserInfo

interface UserRepository {
    suspend fun deleteAccount()
    suspend fun getUserProfile(): UserInfo
}