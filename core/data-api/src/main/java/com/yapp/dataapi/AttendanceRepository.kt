package com.yapp.dataapi

import com.yapp.model.AttendStatistics
import com.yapp.model.AttendanceHistoryList
import com.yapp.model.AttendanceInfo

interface AttendanceRepository {
    suspend fun getAttendanceStatistics(): AttendStatistics
    suspend fun getAttendanceHistory(): AttendanceHistoryList
    suspend fun postAttendance(attendance: AttendanceInfo)
}
