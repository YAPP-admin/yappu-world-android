package com.yapp.core.data.remote.model.response

import com.yapp.model.SignUpResult
import kotlinx.serialization.Serializable
import java.util.Optional

@Serializable
data class SignUpResponse(
    val accessToken: String,
    val refreshToken: String,
)

internal fun Optional<SignUpResponse>.toModel(): SignUpResult {
    return if (isPresent) SignUpResult.Complete else SignUpResult.Pending
}