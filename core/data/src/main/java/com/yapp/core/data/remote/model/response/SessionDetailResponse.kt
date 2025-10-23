package com.yapp.core.data.remote.model.response

import com.yapp.model.SessionDetailInfo
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class SessionDetailResponse(
    val id: String,
    val progressPhase: String,
    val title: String,
    val startDateTime: String,
    val startDayOfWeek: String,
    val endDateTime: String,
    val endDayOfWeek: String,
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
        startDateTime = LocalDateTime.parse(startDateTime, DateTimeFormatter.ISO_DATE_TIME),
        endDateTime = LocalDateTime.parse(endDateTime, DateTimeFormatter.ISO_DATE_TIME),
        place = place ?: "",
        address = address ?: "",
        latitude = latitude ?: 0.0,
        longitude = longitude ?: 0.0,
        notices = notices.map { it.toNoticeModel() }
    )
}