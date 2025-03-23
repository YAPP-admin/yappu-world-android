package com.yapp.core.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
internal data class CheckEmailRequest(
    val email : String,
)