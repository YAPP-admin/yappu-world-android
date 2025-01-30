package com.yapp.core.data.remote.api

import com.yapp.core.data.remote.model.response.PositionConfigResponse
import retrofit2.http.GET

internal interface ConfigApi {
    @GET("v1/positions")
    suspend fun getPositionConfigs(): List<PositionConfigResponse>
}