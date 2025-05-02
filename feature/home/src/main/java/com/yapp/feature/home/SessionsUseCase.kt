package com.yapp.feature.home

import com.yapp.dataapi.ScheduleRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SessionsUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
){
    operator fun invoke() = scheduleRepository.getSessions().map { sessions ->
        sessions.map { session ->
            HomeState.Session(
                id = session.id,
                title = session.name,
                date = session.date,
                place = session.place.orEmpty(),
                startTime = session.time.orEmpty(),
                endTime = session.endTime.orEmpty(),
                startDayOfWeek = session.startDayOfWeek
            )
        }
    }
}
