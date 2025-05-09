package com.yapp.core.data.remote.api

import com.yapp.core.data.remote.model.request.CheckEmailRequest
import com.yapp.core.data.remote.model.request.LoginRequest
import com.yapp.core.data.remote.model.request.ReissueTokenRequest
import com.yapp.core.data.remote.model.request.SignUpRequest
import com.yapp.core.data.remote.model.response.LoginResponse
import com.yapp.core.data.remote.model.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.Optional

internal interface AuthApi {
    @POST("v1/auth/sign-up")
    suspend fun signUp(
        @Body request: SignUpRequest,
    ): Optional<SignUpResponse>

    @POST("v1/auth/login")
    suspend fun login(
        @Body request : LoginRequest
    ) : LoginResponse

    @POST("v1/auth/reissue-token")
    suspend fun reissueToken(
        @Body request: ReissueTokenRequest
    ): LoginResponse

    @POST("v1/auth/check-email")
    suspend fun checkEmail(
        @Body request: CheckEmailRequest
    )
}