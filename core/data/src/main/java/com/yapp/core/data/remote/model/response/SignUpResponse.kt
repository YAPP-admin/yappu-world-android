package com.yapp.core.data.remote.model.response

import com.yapp.model.YappJWT
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    val accessToken: String,
    val refreshToken: String,
) {
    fun toModel() = YappJWT(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}