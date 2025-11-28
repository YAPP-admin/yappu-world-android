package com.yapp.testing.repository

import com.yapp.dataapi.OperationsRepository
import com.yapp.testing.data.OperationsTestData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeOperationsRepository(
    initialPositions: List<String> = OperationsTestData.positionConfigs(),
    var usageInquiryLink: String = "https://example.com/inquiry",
    var termsOfServiceLink: String = "https://example.com/terms",
    var privacyPolicyLink: String = "https://example.com/privacy",
    var basicRuleLink: String = "https://example.com/rules",
    var storedAppVersion: String = "1.0.0",
    var forceUpdateRequired: Boolean = false
) : OperationsRepository {


    private val positionConfigs = MutableStateFlow(initialPositions)
    var basicRuleRequestCount: Int = 0
        private set

    override fun getPositionConfigs(): Flow<List<String>> = positionConfigs

    override suspend fun getUsageInquiryLink(): String = usageInquiryLink

    override suspend fun getTermsOfServiceLink(): String = termsOfServiceLink

    override suspend fun getPrivacyPolicyLink(): String = privacyPolicyLink

    override suspend fun getBasicRuleLink(): String {
        basicRuleRequestCount++
        return basicRuleLink
    }

    override fun getAppVersion(): String = storedAppVersion

    override suspend fun isForceUpdateRequired(): Boolean = forceUpdateRequired

    fun emitPositionConfigs(configs: List<String>) {
        positionConfigs.value = configs
    }
}
