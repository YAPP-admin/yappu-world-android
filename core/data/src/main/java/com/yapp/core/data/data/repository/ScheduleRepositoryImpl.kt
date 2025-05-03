package com.yapp.core.data.data.repository

import com.yapp.core.data.remote.api.ScheduleApi
import com.yapp.core.data.remote.model.response.SessionResponse
import com.yapp.core.data.remote.model.response.toDateGroupedScheduleList
import com.yapp.dataapi.ScheduleRepository
import com.yapp.model.HomeSession
import com.yapp.model.ScheduleList
import com.yapp.model.UpcomingSessionInfo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleApi: ScheduleApi
): ScheduleRepository {

    private var sessionsCache: List<SessionResponse.Session>? = null
    // Pair<year, month> to ScheduleList
    private var scheduleCache: MutableMap<Pair<Int, Int>, ScheduleList> = mutableMapOf()
    private var upcomingSessionCache: UpcomingSessionInfo? = null

    override fun getSessions() = flow {
        val sessions = scheduleApi.getSessions().sessions.toDateGroupedScheduleList()

        val upcomingSessionId = scheduleApi.getSessions().upcomingSessionId
        emit(HomeSession(sessions, upcomingSessionId))
    }

    override suspend fun getDateGroupedSessions(): ScheduleList {
        return sessionsCache?.let {
            ScheduleList(it.toDateGroupedScheduleList())
        } ?: scheduleApi.getSessions().sessions
            .also { sessionsCache = it }
            .let { ScheduleList(it.toDateGroupedScheduleList()) }
    }

    override suspend fun getUpcomingSessions(): UpcomingSessionInfo {
        return upcomingSessionCache ?: scheduleApi.getUpcomingSessions()
            .toUpcomingSessionInfoModel()
            .also { upcomingSessionCache = it }
    }

    override suspend fun getSchedules(year: Int, month: Int): ScheduleList {
        val key = year to month
        scheduleCache[key]?.let { scheduleList ->
            return scheduleList
        } ?: run {
            val scheduleList = scheduleApi.getSchedules(year, month).toScheduleListModel()
            scheduleCache[key] = scheduleList
            return scheduleList
        }
    }

    override suspend fun refreshDateGroupedSessions(): ScheduleList {
        return scheduleApi.getSessions().sessions
            .also { sessionsCache = it }
            .let { ScheduleList(it.toDateGroupedScheduleList()) }
    }

    override suspend fun refreshUpcomingSessions(): UpcomingSessionInfo {
        return scheduleApi.getUpcomingSessions()
            .toUpcomingSessionInfoModel()
            .also { upcomingSessionCache = it }
    }

    override suspend fun refreshSchedules(year: Int, month: Int): ScheduleList {
        return scheduleApi.getSchedules(year, month)
            .toScheduleListModel()
            .also { scheduleList ->
                scheduleCache[Pair(year, month)] = scheduleList
            }
    }
}
