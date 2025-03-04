package com.yapp.core.data.data.repository

import com.yapp.core.data.local.SecurityPreferences
import com.yapp.core.data.remote.api.UserApi
import com.yapp.dataapi.UserRepository
import com.yapp.model.UserInfo
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val securityPreferences: SecurityPreferences,
): UserRepository {

    override suspend fun deleteAccount() {
        userApi.deleteUser()
        securityPreferences.clearAll()
    }

    override suspend fun getUserProfile(): UserInfo = userApi.getUserProfile().toModel()
}