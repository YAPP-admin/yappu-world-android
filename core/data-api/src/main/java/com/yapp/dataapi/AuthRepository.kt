package com.yapp.dataapi

import com.yapp.model.SignUpInfo
import com.yapp.model.SignUpResult

interface AuthRepository {
    suspend fun signUp(
        request: SignUpInfo,
    ): SignUpResult

    suspend fun clearTokens()

    suspend fun login(
        email: String,
        password: String
    )

    suspend fun checkEmail(email: String)
}