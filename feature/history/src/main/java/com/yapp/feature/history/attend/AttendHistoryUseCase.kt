package com.yapp.feature.history.attend

import com.yapp.dataapi.AttendanceRepository
import com.yapp.dataapi.SessionRepository
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

internal class AttendHistoryUseCase @Inject constructor(
    private val attendanceRepository: AttendanceRepository,
    private val sessionRepository: SessionRepository
){
    operator fun invoke() = combine(
        attendanceRepository.getAttendanceStatistics(),
        sessionRepository.getSessions()
    ) { attendStatistics, sessions ->
        attendStatistics to sessions
    }
}
