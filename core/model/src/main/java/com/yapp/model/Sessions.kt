package com.yapp.model

data class HomeSession(
    val sessions: List<DateGroupedSchedule>,
    val upcomingSessionId: String?
)

