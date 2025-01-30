package com.yapp.dataapi

interface AuthorizedUserRepository {
    suspend fun clearTokens()
}