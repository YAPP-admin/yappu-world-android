package com.yapp.core.data.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import com.yapp.core.data.PositionConfigs
import com.yapp.core.data.data.Dispatcher
import com.yapp.core.data.data.YappDispatchers
import com.yapp.core.data.remote.api.OperationsApi
import com.yapp.core.data.remote.model.response.PositionConfigResponse
import com.yapp.dataapi.OperationsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext private val context: Context,
) : OperationsRepository {
    private var usageInquiryLink: String? = null
    private var termsOfServiceLink: String? = null
    private var privacyPolicyLink: String? = null

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

    override suspend fun getUsageInquiryLink(): String {
        return usageInquiryLink ?: operationsApi.getUsageInquiryLink().link.also {
            usageInquiryLink = it
        }
    }

    override suspend fun getTermsOfServiceLink(): String {
        return termsOfServiceLink ?: operationsApi.getTermsOfServiceLink().link.also {
            termsOfServiceLink = it
        }
    }

    override suspend fun getPrivacyPolicyLink(): String {
        return privacyPolicyLink ?: operationsApi.getPrivacyPolicyLink().link.also {
            privacyPolicyLink = it
        }
    }

    override fun getAppVersion(): String {
        return context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: ""
    }

    override suspend fun isForceUpdateRequired(): Boolean {
        val version = getAppVersion()
        return operationsApi.isForceUpdateRequired(
            version = version,
        ).needForceUpdate
    }
}