package com.yapp.core.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SessionResponse(
    val id: String,
    val name: String,
    val place: String?,
    val date: String,
    val endDate: String?,
    val time: String?,
    val type: String,
    val progressPhase: String
)
