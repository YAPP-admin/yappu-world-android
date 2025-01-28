package com.yapp.data_api

import com.yapp.model.SignUpInfo
import com.yapp.model.SignUpResult

interface UnAuthorizedUserRepository {
    suspend fun signUp(
        request: SignUpInfo,
    ): SignUpResult
}