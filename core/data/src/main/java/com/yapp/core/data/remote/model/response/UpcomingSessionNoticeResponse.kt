package com.yapp.core.data.remote.model.response

import com.yapp.model.UpcomingSessionNotice
import kotlinx.serialization.Serializable

@Serializable
data class UpcomingSessionNoticeResponse(
    val id: String,
    val title: String
)

fun UpcomingSessionNoticeResponse.toModel() = UpcomingSessionNotice(
    id = id,
    title = title,
)
