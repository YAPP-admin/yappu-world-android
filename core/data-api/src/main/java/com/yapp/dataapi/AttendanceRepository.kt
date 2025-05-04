package com.yapp.dataapi

import com.yapp.model.AttendStatistics
import com.yapp.model.AttendanceInfo
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    fun getAttendanceStatistics(): Flow<AttendStatistics>
    suspend fun postAttendance(attendance: AttendanceInfo)
}
