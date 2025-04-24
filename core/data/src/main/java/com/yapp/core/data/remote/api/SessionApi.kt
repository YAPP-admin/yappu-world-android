package com.yapp.core.data.remote.api

import com.yapp.core.data.remote.model.response.SessionResponse
import retrofit2.http.GET

interface SessionApi {
    @GET("v1/sessions")
    suspend fun getSessions(): SessionResponse
}
