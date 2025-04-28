package com.yapp.feature.history.attend

import com.yapp.dataapi.AttendanceRepository
import com.yapp.dataapi.ScheduleRepository
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

internal class AttendHistoryUseCase @Inject constructor(
    private val attendanceRepository: AttendanceRepository,
    private val scheduleRepository: ScheduleRepository
){
    operator fun invoke() = combine(
        attendanceRepository.getAttendanceStatistics(),
        scheduleRepository.getSessions()
    ) { attendStatistics, sessions ->
        attendStatistics to sessions
    }
}
