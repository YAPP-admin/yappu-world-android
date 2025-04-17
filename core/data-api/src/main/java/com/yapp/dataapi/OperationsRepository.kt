package com.yapp.dataapi

import kotlinx.coroutines.flow.Flow

interface OperationsRepository {
    fun getPositionConfigs(): Flow<List<String>>

    fun getUsageInquiryLink(): Flow<String>

    fun getTermsOfServiceLink(): Flow<String>

    fun getPrivacyPolicyLink(): Flow<String>

    fun getAppVersion(): String

    suspend fun isForceUpdateRequired(): Boolean
}