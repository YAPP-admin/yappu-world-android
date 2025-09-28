package com.yapp.core.data.remote.model.response

import com.yapp.model.SessionDetailInfo
import kotlinx.serialization.Serializable

@Serializable
data class SessionDetailResponse(
    val id: String,
    val progressPhase: String,
    val title: String,
    val startDate: String,
    val startTime: String?,
    val startDayOfWeek: String,
    val endDate: String?,
    val endTime: String?,
    val endDayOfWeek: String?,
    val place: String?,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val notices: List<NoticeData>
)

fun SessionDetailResponse.toSessionDetailInfo(): SessionDetailInfo {
    return SessionDetailInfo(
        id = id,
        progressPhase = progressPhase.toSessionProgressPhase(),
        title = title,
        startDate = startDate,
        startTime = startTime?.parseTimeToDisplayString(),
        startDayOfWeek = startDayOfWeek,
        endDate = endDate,
        endTime = endTime?.parseTimeToDisplayString(),
        endDayOfWeek = endDayOfWeek,
        place = place ?: "",
        address = address ?: "",
        latitude = latitude ?: 0.0,
        longitude = longitude ?: 0.0,
        notices = notices.map { it.toNoticeModel() }
    )
}

private fun String.parseTimeToDisplayString(): String {
    val parts = split(":")
    if (parts.size < 2) return this

    val hour = parts[0].toIntOrNull() ?: return this
    val minute = parts[1].toIntOrNull() ?: 0

    val period = if (hour < 12) "오전" else "오후"
    val displayHour = when {
        hour == 0 -> 12
        hour > 12 -> hour - 12
        else -> hour
    }

    return if (minute > 0) {
        "$period ${displayHour}시 ${minute}분"
    } else {
        "$period ${displayHour}시"
    }
}