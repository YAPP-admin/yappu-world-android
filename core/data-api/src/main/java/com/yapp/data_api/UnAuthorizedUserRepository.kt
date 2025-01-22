package com.yapp.data_api

import com.yapp.model.SignUpInfo
import com.yapp.model.YappJWT

interface UnAuthorizedUserRepository {
    suspend fun signUp(
        request: SignUpInfo,
    ): YappJWT?
}