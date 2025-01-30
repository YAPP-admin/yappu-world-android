package com.yapp.core.data.remote.api

import com.yapp.core.data.remote.model.request.SignUpRequest
import com.yapp.core.data.remote.model.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.Optional

internal interface UnAuthorizedUserApi {
    @POST("v1/auth/sign-up")
    suspend fun signUp(
        @Body request: SignUpRequest,
    ): Optional<SignUpResponse>
}