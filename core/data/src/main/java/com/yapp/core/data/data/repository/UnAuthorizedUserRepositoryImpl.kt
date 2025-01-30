package com.yapp.core.data.data.repository

import com.yapp.core.data.data.Dispatcher
import com.yapp.core.data.data.YappDispatchers
import com.yapp.core.data.local.PositionConfigDao
import com.yapp.core.data.local.SecurityPreferences
import com.yapp.core.data.remote.api.UnAuthorizedUserApi
import com.yapp.core.data.remote.model.request.toData
import com.yapp.core.data.remote.model.response.toModel
import com.yapp.dataapi.UnAuthorizedUserRepository
import com.yapp.model.SignUpInfo
import com.yapp.model.SignUpResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull

internal class UnAuthorizedUserRepositoryImpl @Inject constructor(
    private val api: UnAuthorizedUserApi,
    private val securityPreferences: SecurityPreferences,
    private val dao: PositionConfigDao,
    @Dispatcher(YappDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
): UnAuthorizedUserRepository {

    override suspend fun signUp(request: SignUpInfo): SignUpResult {
        val positionConfigs = withContext(ioDispatcher) {
            dao.getPositionConfigs()
        }

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