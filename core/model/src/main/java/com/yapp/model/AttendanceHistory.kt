package com.yapp.model

data class AttendanceHistoryList(
    val histories: List<AttendanceHistory>
)

data class AttendanceHistory(
    val checkedInAt: String?,
    val attendanceStatus: AttendanceStatus
)
