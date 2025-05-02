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
                title = session.name,
                date = session.date,
                place = session.place.orEmpty(),
                startTime = session.startTime.orEmpty(),
                endTime = session.endTime.orEmpty()
            )
        }
    }
}
