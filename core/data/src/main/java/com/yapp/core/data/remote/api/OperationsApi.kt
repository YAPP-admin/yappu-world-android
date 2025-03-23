package com.yapp.core.data.remote.api

import com.yapp.core.data.remote.model.response.PositionConfigResponse
import retrofit2.http.GET

internal interface OperationsApi {
    @GET("v1/operations/positions")
    suspend fun getPositionConfigs(): PositionConfigResponse
}