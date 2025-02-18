package com.yapp.core.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginResponse (
    val accessToken : String,
    val refreshToken : String
)
