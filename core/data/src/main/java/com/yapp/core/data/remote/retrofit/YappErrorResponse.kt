package com.yapp.core.data.remote.retrofit

import kotlinx.serialization.Serializable

@Serializable
data class YappErrorResponse(
    val isSuccess: Boolean,
    val errorCode: String,
    val message: String
)
