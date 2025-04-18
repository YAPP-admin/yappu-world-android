package com.yapp.core.data.data.repository

import com.yapp.core.data.remote.api.AttendanceApi
import com.yapp.dataapi.AttendanceRepository
import com.yapp.model.AttendStatistics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class AttendanceRepositoryImpl @Inject constructor(
    private val attendApi: AttendanceApi
): AttendanceRepository{
    override fun getAttendanceStatistics(): Flow<AttendStatistics> = flow {
        emit(attendApi.getAttendanceStatistics().toModel())
    }
}
