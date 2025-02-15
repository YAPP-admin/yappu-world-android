package com.yapp.core.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class FcmTokenRequest(
    val fcmToken: String,
)
