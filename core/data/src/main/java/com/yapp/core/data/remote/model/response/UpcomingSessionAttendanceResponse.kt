package com.yapp.core.data.remote.model.response

import com.yapp.core.data.remote.model.response.ScheduleResponse.Companion.toAttendanceStatus
import com.yapp.model.UpcomingSessionInfo
import kotlinx.serialization.Serializable
import kotlin.math.max

@Serializable
data class UpcomingSessionAttendanceResponse(
    val sessionId: String,
    val name: String,
    val startDate: String,
    val startDayOfWeek: String,
    val endDate: String,
    val endDayOfWeek: String,
    val startTime: String?,
    val endTime: String?,
    val place: String?,
    val relativeDays: Int,
    val canCheckIn: Boolean,
    val status: String?
) {
    fun toUpcomingSessionInfoModel() = UpcomingSessionInfo(
        sessionId = sessionId,
        name = name,
        startDate = startDate,
        startDayOfTheWeek = startDayOfWeek,
        endDate = endDate,
        endDayOfTheWeek = endDayOfWeek,
        startTime = startTime,
        endTime = endTime,
        location = place,
        remainingDays = max(0, relativeDays),
        canCheckIn = canCheckIn,
        status = status.toAttendanceStatus()
    )
}
