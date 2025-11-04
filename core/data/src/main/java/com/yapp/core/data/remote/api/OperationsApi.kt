package com.yapp.core.data.remote.api

import com.yapp.core.data.remote.model.response.ForceUpdateResponse
import com.yapp.core.data.remote.model.response.LinkResponse
import com.yapp.core.data.remote.model.response.PositionConfigResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface OperationsApi {
    /***
     * 직군정보
     ***/
    @GET("v1/operations/positions")
    suspend fun getPositionConfigs(): PositionConfigResponse
    /***
     * 이용 문의 링크
     ***/
    @GET("v1/operations/links/usage-inquiry")
    suspend fun getUsageInquiryLink(): LinkResponse
    /***
     * 이용약관 링크
     ***/
    @GET("v1/operations/links/terms-of-service")
    suspend fun getTermsOfServiceLink(): LinkResponse
    /***
     * 개인정보 처리방침 링크
     ***/
    @GET("v1/operations/links/privacy-policy")
    suspend fun getPrivacyPolicyLink(): LinkResponse
    /***
     * 기본 규칙 링크
     ***/
    @GET("v1/operations/links/basic-rule")
    suspend fun getBasicRuleLink(): LinkResponse
    /***
     * 강제 업데이트 정보
     ***/
    @GET("v1/operations/force-update")
    suspend fun isForceUpdateRequired(
        @Query("version") version: String,
        @Query("platform") platform: String = "ANDROID"
    ): ForceUpdateResponse
}