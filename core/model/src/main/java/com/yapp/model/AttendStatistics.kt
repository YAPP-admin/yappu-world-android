package com.yapp.model

data class AttendStatistics(
    val totalSessionCount: Int,
    val remainingSessionCount: Int,
    val sessionProgressRate: Double,
    val attendancePoint: Int,
    val attendanceCount: Int,
    val lateCount: Int,
    val absenceCount: Int,
    val latePassCount: Int
)
