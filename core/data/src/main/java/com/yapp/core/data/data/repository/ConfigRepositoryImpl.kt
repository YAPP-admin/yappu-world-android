package com.yapp.core.data.data.repository

import androidx.datastore.core.DataStore
import com.yapp.core.data.PositionConfigs
import com.yapp.core.data.data.Dispatcher
import com.yapp.core.data.data.YappDispatchers
import com.yapp.core.data.remote.api.OperationsApi
import com.yapp.core.data.remote.model.response.PositionConfigResponse
import com.yapp.dataapi.ConfigRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class ConfigRepositoryImpl @Inject constructor(
    private val operationsApi: OperationsApi,
    private val dataStore: DataStore<PositionConfigs>,
    @Dispatcher(YappDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ConfigRepository {

    override fun getPositionConfigs(): Flow<List<String>> = flow {
        val localPositionConfigs = dataStore.data.firstOrNull()
        if (localPositionConfigs != null) {
            emit(localPositionConfigs.configsList.map { it.label })
        }

        val remotePositionConfigs = operationsApi.getPositionConfigs()
        emit(remotePositionConfigs.positions.map { it.label })

        updatePositionConfigs(remotePositionConfigs)
    }.flowOn(ioDispatcher)

    private suspend fun updatePositionConfigs(positionConfigs: PositionConfigResponse) {
        dataStore.updateData { currentData ->
            currentData.toBuilder()
                .clearConfigs()
                .addAllConfigs(positionConfigs.toProto())
                .build()
        }
    }
}