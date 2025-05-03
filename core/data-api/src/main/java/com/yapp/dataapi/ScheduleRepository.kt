package com.yapp.dataapi

import com.yapp.model.DateGroupedSchedule
import com.yapp.model.ScheduleList
import com.yapp.model.UpcomingSessionInfo
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getSessions(): Flow<Pair<List<DateGroupedSchedule>, String?>>

    suspend fun getDateGroupedSessions(): ScheduleList

    suspend fun getUpcomingSessions(): UpcomingSessionInfo

    suspend fun getSchedules(year: Int, month: Int): ScheduleList

    suspend fun refreshDateGroupedSessions(): ScheduleList

    suspend fun refreshUpcomingSessions(): UpcomingSessionInfo

    suspend fun refreshSchedules(year: Int, month: Int): ScheduleList
}
