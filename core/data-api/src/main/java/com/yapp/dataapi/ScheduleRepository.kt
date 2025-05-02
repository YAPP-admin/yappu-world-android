package com.yapp.dataapi

import com.yapp.model.ScheduleList
import com.yapp.model.Sessions
import com.yapp.model.UpcomingSessionInfo
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getSessions(): Flow<List<Sessions>>

    suspend fun getDateGroupedSessions(): ScheduleList

    suspend fun getUpcomingSessions(): UpcomingSessionInfo

    suspend fun getSchedules(year: Int, month: Int): ScheduleList
}
