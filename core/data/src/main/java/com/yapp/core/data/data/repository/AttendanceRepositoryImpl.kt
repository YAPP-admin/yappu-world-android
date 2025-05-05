package com.yapp.core.data.data.repository

import com.yapp.core.data.remote.api.AttendanceApi
import com.yapp.core.data.remote.model.request.Attendance
import com.yapp.core.data.remote.model.response.toAttendanceHistoryListModel
import com.yapp.dataapi.AttendanceRepository
import com.yapp.model.AttendStatistics
import com.yapp.model.AttendanceHistoryList
import com.yapp.model.AttendanceInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class AttendanceRepositoryImpl @Inject constructor(
    private val attendApi: AttendanceApi
): AttendanceRepository{
    override fun getAttendanceStatistics(): Flow<AttendStatistics> = flow {
        emit(attendApi.getAttendanceStatistics().toModel())
    }

    override suspend fun getAttendanceHistory(): AttendanceHistoryList =
        attendApi.getAttendanceHistory().toAttendanceHistoryListModel()

    override suspend fun postAttendance(attendance: AttendanceInfo) {
        attendApi.postAttendance(Attendance(attendance.sessionId, attendance.attendanceCode))
    }
}
