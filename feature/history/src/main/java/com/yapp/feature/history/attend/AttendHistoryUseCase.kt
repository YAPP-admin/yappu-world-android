package com.yapp.feature.history.attend

import com.yapp.dataapi.AttendanceRepository
import com.yapp.dataapi.ScheduleRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

internal class AttendHistoryUseCase @Inject constructor(
    private val attendanceRepository: AttendanceRepository,
    private val scheduleRepository: ScheduleRepository
){
    suspend operator fun invoke() = combine(
        attendanceRepository.getAttendanceStatistics(),
        flowOf(scheduleRepository.getSessions())
    ) { attendStatistics, sessions ->
        attendStatistics to sessions.sessions.flatMap { it.schedules }
    }
}
