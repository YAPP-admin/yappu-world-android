package com.yapp.core.data.data.repository

import com.yapp.core.data.remote.api.ScheduleApi
import com.yapp.core.data.remote.model.response.toDateGroupedScheduleList
import com.yapp.dataapi.ScheduleRepository
import com.yapp.model.ScheduleList
import com.yapp.model.Sessions
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleApi: ScheduleApi
): ScheduleRepository{
    override fun getSessions() = flow {
        emit(scheduleApi.getSessions().sessions.map { result ->
            Sessions(
                id = result.id,
                name = result.name,
                place = result.place,
                date = result.date,
                endDate = result.endDate,
                startDayOfWeek = result.startDayOfWeek,
                endDayOfWeek = result.endDayOfWeek,
                time = result.time,
                endTime = result.endTime,
                relativeDays = result.relativeDays,
                type = Sessions.AttendType.valueOf(result.type),
                progressPhase = result.progressPhase,
                attendanceStatus = result.attendanceStatus
            )
        })
    }

    override fun getDateGroupedSessions() = flow {
        emit(ScheduleList(scheduleApi.getSessions().sessions.toDateGroupedScheduleList()))
    }

    override fun getUpcomingSessions() = flow {
        emit(scheduleApi.getUpcomingSessions().toUpcomingSessionInfoModel())
    }

    override fun getSchedules(year: Int, month: Int) = flow {
        emit(scheduleApi.getSchedules(year, month).toScheduleListModel())
    }
}
