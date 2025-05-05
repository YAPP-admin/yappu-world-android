package com.yapp.core.data.remote.model.response

import com.yapp.core.data.remote.model.response.ScheduleResponse.Companion.toAttendanceStatus
import com.yapp.model.AttendanceHistory
import com.yapp.model.AttendanceHistoryList
import com.yapp.model.AttendanceStatus
import kotlinx.serialization.Serializable

@Serializable
data class AttendanceHistoryListResponse(
    val histories: List<AttendanceHistoryData>,
) {
    @Serializable
    data class AttendanceHistoryData(
        val sessionId: String,
        val name: String,
        val checkedInAt: String?,
        val attendanceStatus: String?,
    )
}

fun AttendanceHistoryListResponse.toAttendanceHistoryListModel() = AttendanceHistoryList(
    histories = histories.map { it.toAttendanceHistoryModel() }
)

fun AttendanceHistoryListResponse.AttendanceHistoryData.toAttendanceHistoryModel() = AttendanceHistory(
    checkedInAt = checkedInAt,
    attendanceStatus = attendanceStatus.toAttendanceStatus() ?: AttendanceStatus.ATTENDED,
)