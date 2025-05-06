package com.yapp.domain

import com.yapp.dataapi.ScheduleRepository
import com.yapp.model.HomeSessionList
import com.yapp.model.UpcomingSessionInfo
import javax.inject.Inject

class SessionsUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
){
    data class HomeSessions(
        val upcomingSessionInfo: UpcomingSessionInfo,
        val sessions: HomeSessionList
    )

    suspend operator fun invoke(): HomeSessions {
        val upcomingSession = scheduleRepository.getUpcomingSessions()
        val sessions = scheduleRepository.getSessions()

        return HomeSessions(
            upcomingSessionInfo = upcomingSession,
            sessions = HomeSessionList(sessions.sessions, sessions.upcomingSessionId)
        )
    }
}
