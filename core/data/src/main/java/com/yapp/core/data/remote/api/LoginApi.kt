package com.yapp.core.data.remote.api

import com.yapp.core.data.remote.model.request.LoginRequest
import com.yapp.core.data.remote.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

internal interface LoginApi {
    @POST("v1/auth/login")
    suspend fun login(
        @Body request : LoginRequest
    ) : LoginResponse
}
