package com.yapp.model

data class AttendanceHistoryList(
    val histories: List<AttendanceHistory>
)

data class AttendanceHistory(
    val name: String,
    val checkedInAt: String?,
    val attendanceStatus: AttendanceStatus
)
