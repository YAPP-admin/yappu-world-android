package com.yapp.model

data class HomeSessionList(
    val sessions: List<HomeSession>,
    val upcomingSessionId: String?
)

data class HomeSession(
    val id: String,
    val name: String,
    val place: String,
    val date: String,
    val dayOfWeek: String,
    val relativeDays: Int,
    val startTime: String?,
    val endTime: String?,
    val progressPhase: ScheduleProgressPhase,
    val attendanceStatus: AttendanceStatus?
)
