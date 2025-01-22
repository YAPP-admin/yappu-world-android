package com.yapp.core.data.remote.retrofit

import kotlinx.serialization.Serializable

@Serializable
data class YappResponse<T>(
    val data: T? = null
)

