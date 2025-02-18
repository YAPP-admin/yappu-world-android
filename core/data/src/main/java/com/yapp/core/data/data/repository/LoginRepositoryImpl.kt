package com.yapp.core.data.data.repository

import com.yapp.core.data.local.SecurityPreferences
import com.yapp.core.data.remote.api.LoginApi
import com.yapp.core.data.remote.model.request.LoginRequest
import com.yapp.dataapi.LoginRepository
import javax.inject.Inject

internal class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val securityPreferences: SecurityPreferences,
) : LoginRepository {
    override suspend fun login(email: String, password: String){
        val response = loginApi.login(LoginRequest(email = email, password = password))
        response.let {
            securityPreferences.setAccessToken(it.accessToken)
            securityPreferences.setRefreshToken(it.refreshToken)
        }
    }
}