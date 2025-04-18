package com.yapp.dataapi

import com.yapp.model.AttendStatistics
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    fun getAttendanceStatistics(): Flow<AttendStatistics>
}
