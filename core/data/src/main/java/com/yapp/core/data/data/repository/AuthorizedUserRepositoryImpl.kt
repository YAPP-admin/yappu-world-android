package com.yapp.core.data.data.repository

import com.yapp.core.data.local.SecurityPreferences
import com.yapp.core.data.remote.api.UnAuthorizedUserApi
import com.yapp.core.data.remote.model.request.toData
import com.yapp.core.data.remote.model.response.toModel
import com.yapp.dataapi.AuthorizedUserRepository
import com.yapp.dataapi.UnAuthorizedUserRepository
import com.yapp.model.SignUpInfo
import com.yapp.model.SignUpResult
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull

internal class AuthorizedUserRepositoryImpl @Inject constructor(
    private val securityPreferences: SecurityPreferences,
): AuthorizedUserRepository {

    override suspend fun clearTokens() {
        securityPreferences.clearAll()
    }
}