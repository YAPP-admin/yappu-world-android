package com.yapp.core.data.data.repository

import com.yapp.core.data.local.SecurityPreferences
import com.yapp.core.data.remote.api.AuthorizedUserApi
import com.yapp.dataapi.AuthorizedUserRepository
import com.yapp.model.UserInfo
import javax.inject.Inject

internal class AuthorizedUserRepositoryImpl @Inject constructor(
    private val authorizedUserApi: AuthorizedUserApi,
    private val securityPreferences: SecurityPreferences,
): AuthorizedUserRepository {

    override suspend fun clearTokens() {
        securityPreferences.clearAll()
    }

    override suspend fun deleteAccount() {
        authorizedUserApi.deleteUser()
    }

    override suspend fun getUserProfile(): UserInfo = authorizedUserApi.getUserProfile().toModel()
}