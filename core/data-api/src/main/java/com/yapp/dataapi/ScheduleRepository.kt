package com.yapp.dataapi

import com.yapp.model.HomeSessionList
import com.yapp.model.ScheduleList
import com.yapp.model.UpcomingSessionInfo

interface ScheduleRepository {
    suspend fun getSessions(): HomeSessionList

    suspend fun getDateGroupedSessions(): ScheduleList

    suspend fun getUpcomingSessions(): UpcomingSessionInfo

    suspend fun getSchedules(year: Int, month: Int): ScheduleList

    suspend fun refreshDateGroupedSessions(): ScheduleList

    suspend fun refreshUpcomingSessions(): UpcomingSessionInfo

    suspend fun refreshSchedules(year: Int, month: Int): ScheduleList
}
