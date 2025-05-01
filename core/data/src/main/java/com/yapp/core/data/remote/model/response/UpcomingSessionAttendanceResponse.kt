package com.yapp.core.data.remote.model.response

import com.yapp.core.data.remote.model.response.ScheduleResponse.Companion.toAttendanceStatus
import com.yapp.model.UpcomingSessionInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.max

@Serializable
data class UpcomingSessionAttendanceResponse(
    @SerialName("sessionId")
    val sessionId: String,
    @SerialName("name")
    val name: String,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("startDayOfWeek")
    val startDayOfWeek: String,
    @SerialName("endDate")
    val endDate: String,
    @SerialName("endDayOfWeek")
    val endDayOfWeek: String,
    @SerialName("startTime")
    val startTime: String?,
    @SerialName("endTime")
    val endTime: String?,
    @SerialName("place")
    val place: String?,
    @SerialName("relativeDays")
    val relativeDays: Int,
    @SerialName("canCheckIn")
    val canCheckIn: Boolean,
    @SerialName("status")
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
