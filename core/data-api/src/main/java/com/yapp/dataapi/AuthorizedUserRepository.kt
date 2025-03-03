package com.yapp.dataapi

import com.yapp.model.UserInfo

interface AuthorizedUserRepository {
    suspend fun clearTokens()
    suspend fun deleteAccount()
    suspend fun getUserProfile(): UserInfo
}