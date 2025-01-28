package com.yapp.core.data.data.repository

import com.yapp.core.data.local.SecurityPreferences
import com.yapp.core.data.remote.api.UnAuthorizedUserApi
import com.yapp.core.data.remote.model.request.toData
import com.yapp.core.data.remote.model.response.toModel
import com.yapp.data_api.UnAuthorizedUserRepository
import com.yapp.model.SignUpInfo
import com.yapp.model.SignUpResult
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull

internal class UnAuthorizedUserRepositoryImpl @Inject constructor(
    private val api: UnAuthorizedUserApi,
    private val securityPreferences: SecurityPreferences,
): UnAuthorizedUserRepository {

    override suspend fun signUp(request: SignUpInfo): SignUpResult {
        val response = api.signUp(
            request.toData()
        )

        response.getOrNull()?.let {
            securityPreferences.setAccessToken(it.accessToken)
            securityPreferences.setRefreshToken(it.refreshToken)
        }

        return response.toModel()
    }
}