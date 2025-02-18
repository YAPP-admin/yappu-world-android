package com.yapp.core.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginRequest (
    val email : String,
    val password : String
)
