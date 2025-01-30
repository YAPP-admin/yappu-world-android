package com.yapp.core.data.data.repository

import com.yapp.core.data.data.Dispatcher
import com.yapp.core.data.data.YappDispatchers
import com.yapp.core.data.local.PositionConfigDao
import com.yapp.core.data.local.SecurityPreferences
import com.yapp.core.data.remote.api.ConfigApi
import com.yapp.core.data.remote.api.UnAuthorizedUserApi
import com.yapp.core.data.remote.model.request.toData
import com.yapp.core.data.remote.model.response.toModel
import com.yapp.dataapi.ConfigRepository
import com.yapp.dataapi.UnAuthorizedUserRepository
import com.yapp.model.SignUpInfo
import com.yapp.model.SignUpResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull

internal class ConfigRepositoryImpl @Inject constructor(
    private val configApi: ConfigApi,
    private val positionConfigDao: PositionConfigDao,
    @Dispatcher(YappDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ConfigRepository {

    override fun getPositionConfigs(): Flow<List<String>> = flow {
        emit(positionConfigDao.getPositionConfigs().map { it.label })

        val remotePositionConfigs = configApi.getPositionConfigs()
        positionConfigDao.replacePositionConfigs(remotePositionConfigs.toEntity())

        emit(positionConfigDao.getPositionConfigs().map { it.label })
    }.flowOn(ioDispatcher)
}