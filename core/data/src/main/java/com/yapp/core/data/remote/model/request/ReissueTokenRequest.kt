package com.yapp.core.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
internal data class ReissueTokenRequest(
    val accessToken : String,
    val refreshToken : String
)