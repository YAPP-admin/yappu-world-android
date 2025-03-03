package com.yapp.core.data.data.repository

import com.yapp.core.data.local.SecurityPreferences
import com.yapp.core.data.remote.api.UnAuthorizedUserApi
import com.yapp.core.data.remote.model.request.LoginRequest
import com.yapp.dataapi.LoginRepository
import timber.log.Timber
import javax.inject.Inject

internal class LoginRepositoryImpl @Inject constructor(
    private val unauthorizedUserApi: UnAuthorizedUserApi,
    private val securityPreferences: SecurityPreferences,
) : LoginRepository {
    override suspend fun login(email: String, password: String) {
        val response = unauthorizedUserApi.login(LoginRequest(email = email, password = password))
        securityPreferences.setAccessToken(response.accessToken)
        securityPreferences.setRefreshToken(response.refreshToken)
    }
}