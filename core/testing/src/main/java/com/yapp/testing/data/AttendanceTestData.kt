@file:Suppress("MagicNumber")

package com.yapp.testing.data

import com.yapp.model.AttendStatistics
import com.yapp.model.AttendanceHistory
import com.yapp.model.AttendanceHistoryList
import com.yapp.model.AttendanceStatus

object AttendanceTestData {
    fun attendanceStatistics(): AttendStatistics = AttendStatistics(
        totalSessionCount = 12,
        remainingSessionCount = 4,
        sessionProgressRate = 0.66,
        attendancePoint = 200,
        attendanceCount = 7,
        lateCount = 2,
        absenceCount = 1,
        latePassCount = 1
    )

    fun attendanceHistory(): AttendanceHistoryList = AttendanceHistoryList(
        histories = listOf(
            AttendanceHistory(
                name = "5주차 전체 세션",
                checkedInAt = "2024-05-11T13:05:00+09:00",
                attendanceStatus = AttendanceStatus.ATTENDED
            ),
            AttendanceHistory(
                name = "4주차 전체 세션",
                checkedInAt = "2024-05-04T13:20:00+09:00",
                attendanceStatus = AttendanceStatus.LATE
            ),
            AttendanceHistory(
                name = "3주차 전체 세션",
                checkedInAt = null,
                attendanceStatus = AttendanceStatus.ABSENT
            )
        )
    )
}
