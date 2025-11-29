package com.yapp.testing.repository

import com.yapp.dataapi.AttendanceRepository
import com.yapp.model.AttendStatistics
import com.yapp.model.AttendanceHistoryList
import com.yapp.model.AttendanceInfo
import com.yapp.testing.data.AttendanceTestData

class FakeAttendanceRepository(
    var attendStatistics: AttendStatistics = AttendanceTestData.attendanceStatistics(),
    var attendanceHistoryList: AttendanceHistoryList = AttendanceTestData.attendanceHistory(),
    var postAttendanceResult: Result<Unit> = Result.success(Unit)
) : AttendanceRepository {

    override suspend fun getAttendanceStatistics(): AttendStatistics = attendStatistics

    override suspend fun getAttendanceHistory(): AttendanceHistoryList = attendanceHistoryList

    override suspend fun postAttendance(attendance: AttendanceInfo) {
        postAttendanceResult.getOrThrow()
    }
}
