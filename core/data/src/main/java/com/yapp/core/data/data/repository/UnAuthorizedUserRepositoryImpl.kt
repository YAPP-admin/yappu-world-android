package com.yapp.core.data.data.repository

import androidx.datastore.core.DataStore
import com.yapp.core.data.PositionConfigs
import com.yapp.core.data.local.SecurityPreferences
import com.yapp.core.data.remote.api.UnAuthorizedUserApi
import com.yapp.core.data.remote.model.request.toData
import com.yapp.core.data.remote.model.response.toModel
import com.yapp.dataapi.UnAuthorizedUserRepository
import com.yapp.model.SignUpInfo
import com.yapp.model.SignUpResult
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull

internal class UnAuthorizedUserRepositoryImpl @Inject constructor(
    private val api: UnAuthorizedUserApi,
    private val securityPreferences: SecurityPreferences,
    private val dataStore: DataStore<PositionConfigs>,
): UnAuthorizedUserRepository {

    override suspend fun signUp(request: SignUpInfo): SignUpResult {
        val positionConfigs = dataStore.data.firstOrNull() ?: PositionConfigs.getDefaultInstance()

        val response = api.signUp(
            request.toData(positionConfigs)
        )

        response.getOrNull()?.let {
            securityPreferences.setAccessToken(it.accessToken)
            securityPreferences.setRefreshToken(it.refreshToken)
        }

        return response.toModel()
    }
}