package com.yapp.testing.repository

import com.yapp.dataapi.ScheduleRepository
import com.yapp.model.HomeSessionList
import com.yapp.model.ScheduleList
import com.yapp.model.SessionDetailInfo
import com.yapp.model.UpcomingSessionInfo
import com.yapp.testing.data.ScheduleTestData

class FakeScheduleRepository(
    var homeSessionList: HomeSessionList = ScheduleTestData.homeSessionList(),
    var dateGroupedScheduleList: ScheduleList = ScheduleTestData.scheduleList(),
    var monthlyScheduleList: ScheduleList = ScheduleTestData.scheduleList(),
    var upcomingSessionInfo: UpcomingSessionInfo = ScheduleTestData.upcomingSessionInfo(),
    sessionDetails: Map<String, SessionDetailInfo> = mapOf(
        ScheduleTestData.sessionDetailInfo().let { it.id to it }
    )
) : ScheduleRepository {

    private val sessionDetailMap = sessionDetails.toMutableMap().also { map ->
        if (map.isEmpty()) {
            val defaultDetail = ScheduleTestData.sessionDetailInfo()
            map[defaultDetail.id] = defaultDetail
        }
    }
    private val monthlySchedulesByKey = mutableMapOf<Pair<Int, Int>, ScheduleList>()

    var refreshUpcomingSessionsCount: Int = 0
        private set

    var lastSessionsRange: Pair<String, String>? = null
        private set
    var lastScheduleYearMonth: Pair<Int, Int>? = null
        private set
    var lastRequestedSessionId: String? = null
        private set

    override suspend fun getSessions(startDate: String, endDate: String): HomeSessionList {
        lastSessionsRange = startDate to endDate
        return homeSessionList
    }

    override suspend fun getDateGroupedSessions(): ScheduleList = dateGroupedScheduleList

    override suspend fun getUpcomingSession(): UpcomingSessionInfo = upcomingSessionInfo

    override suspend fun getSchedules(year: Int, month: Int): ScheduleList {
        return fetchMonthlySchedule(year, month)
    }

    override suspend fun refreshDateGroupedSessions(): ScheduleList = dateGroupedScheduleList

    override suspend fun refreshUpcomingSessions(): UpcomingSessionInfo {
        refreshUpcomingSessionsCount++
        return upcomingSessionInfo
    }

    override suspend fun refreshSchedules(year: Int, month: Int): ScheduleList {
        return fetchMonthlySchedule(year, month)
    }

    override suspend fun getSessionDetail(sessionId: String): SessionDetailInfo {
        lastRequestedSessionId = sessionId
        return sessionDetailMap[sessionId] ?: sessionDetailMap.values.first()
    }

    fun setMonthlySchedule(year: Int, month: Int, scheduleList: ScheduleList) {
        monthlySchedulesByKey[year to month] = scheduleList
    }

    fun setSessionDetail(info: SessionDetailInfo) {
        sessionDetailMap[info.id] = info
    }

    private fun fetchMonthlySchedule(year: Int, month: Int): ScheduleList {
        val key = year to month
        lastScheduleYearMonth = key
        return monthlySchedulesByKey[key] ?: monthlyScheduleList
    }
}
