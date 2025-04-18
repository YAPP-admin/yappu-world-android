package com.yapp.core.data.remote.model.response

import com.yapp.model.AttendStatistics
import com.yapp.model.UserInfo
import kotlinx.serialization.Serializable

@Serializable
data class AttendStatisticsResponse(
    val totalSessionCount: Int,
    val remainingSessionCount: Int,
    val sessionProgressRate: Int,
    val attendancePoint: Int,
    val attendanceCount: Int,
    val lateCount: Int,
    val absenceCount: Int,
    val latePassCount: Int
) {
    fun toModel() = AttendStatistics(
        totalSessionCount = totalSessionCount,
        remainingSessionCount = remainingSessionCount,
        sessionProgressRate = sessionProgressRate,
        attendancePoint = attendancePoint,
        attendanceCount = attendanceCount,
        lateCount = lateCount,
        absenceCount = absenceCount,
        latePassCount = latePassCount
    )
}
