package com.yapp.feature.home

import com.yapp.dataapi.SessionRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SessionsUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
){
    operator fun invoke() = sessionRepository.getSessions().map { sessions ->
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
