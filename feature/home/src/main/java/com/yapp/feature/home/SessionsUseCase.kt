package com.yapp.feature.home

import com.yapp.dataapi.ScheduleRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SessionsUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
){
    operator fun invoke() = scheduleRepository.getSessions().map { (sessions, upcomingSessionId) ->
        sessions.map { session ->
            session.schedules.map { schdule ->
                HomeState.Session(
                    id = schdule.id,
                    title = schdule.name,
                    date = schdule.date,
                    place = schdule.place.orEmpty(),
                    startTime = schdule.time.orEmpty(),
                    endTime = schdule.endTime.orEmpty(),
                    startDayOfWeek = session.dayOfTheWeek,
                    progressPhase = schdule.scheduleProgressPhase
                )
            }
        }.flatten() to upcomingSessionId
    }
}
