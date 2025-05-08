package com.yapp.dataapi

import kotlinx.coroutines.flow.Flow

interface OperationsRepository {
    fun getPositionConfigs(): Flow<List<String>>

    suspend fun getUsageInquiryLink(): String

    suspend fun getTermsOfServiceLink(): String

    suspend fun getPrivacyPolicyLink(): String

    fun getAppVersion(): String

    suspend fun isForceUpdateRequired(): Boolean
}