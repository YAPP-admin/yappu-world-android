package com.yapp.model

data class UpcomingSessionInfo(
    val sessionId: String,
    val name: String,
    val startDate: String,
    val startDayOfTheWeek: String,
    val endDate: String,
    val endDayOfTheWeek: String,
    val startTime: String?,
    val endTime: String?,
    val location: String?,
    val remainingDays: Int,
    val canCheckIn: Boolean,
    val status: AttendanceStatus?
)