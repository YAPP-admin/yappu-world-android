package com.yapp.core.data.remote.api

import com.yapp.core.data.remote.model.response.LinkResponse
import com.yapp.core.data.remote.model.response.PositionConfigResponse
import retrofit2.http.GET

internal interface OperationsApi {
    @GET("v1/operations/positions")
    suspend fun getPositionConfigs(): PositionConfigResponse

    @GET("v1/operations/links/usage-inquiry")
    suspend fun getUsageInquiryLink(): LinkResponse

    @GET("v1/operations/links/terms-of-service")
    suspend fun getTermsOfServiceLink(): LinkResponse

    @GET("v1/operations/links/privacy-policy")
    suspend fun getPrivacyPolicyLink(): LinkResponse
}