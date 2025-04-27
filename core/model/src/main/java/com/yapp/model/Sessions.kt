package com.yapp.model

data class Sessions(
    val id: String,
    val name: String,
    val place: String?,
    val date: String,
    val endDate: String?,
    val startDayOfWeek: String,
    val endDayOfWeek: String?,
    val relativeDays: Int,
    val time: String?,
    val startTime: String?,
    val endTime: String?,
    val type: AttendType,
    val progressPhase: String,
    val attendanceStatus: String?,
) {
    enum class AttendType {
        OFFLINE, TEAM
    }
}
