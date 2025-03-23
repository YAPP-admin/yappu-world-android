package com.yapp.core.data.data.repository

import androidx.datastore.core.DataStore
import com.yapp.core.data.PositionConfigs
import com.yapp.core.data.data.Dispatcher
import com.yapp.core.data.data.YappDispatchers
import com.yapp.core.data.remote.api.OperationsApi
import com.yapp.core.data.remote.model.response.PositionConfigResponse
import com.yapp.dataapi.OperationsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class OperationsRepositoryImpl @Inject constructor(
    private val operationsApi: OperationsApi,
    private val dataStore: DataStore<PositionConfigs>,
    @Dispatcher(YappDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : OperationsRepository {

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

    override fun getUsageInquiryLink(): Flow<String> = flow {
        emit("http://pf.kakao.com/_ixmUxjn/chat")

        emit(operationsApi.getUsageInquiryLink().link)
    }.flowOn(ioDispatcher)

    override fun getTermsOfServiceLink(): Flow<String> = flow {
        emit("https://yapp-workspace.notion.site/48f4eb2ffdd94740979e8a3b37ca260d?pvs=4")

        emit(operationsApi.getTermsOfServiceLink().link)
    }.flowOn(ioDispatcher)

    override fun getPrivacyPolicyLink(): Flow<String> = flow {
        emit("https://yapp-workspace.notion.site/fc24f8ba29c34f9eb30eb945c621c1ca?pvs=4")

        emit(operationsApi.getPrivacyPolicyLink().link)
    }.flowOn(ioDispatcher)
}