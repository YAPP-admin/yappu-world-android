package com.yapp.core.data.data.repository

import com.yapp.core.data.remote.api.ScheduleApi
import com.yapp.dataapi.ScheduleRepository
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
                time = result.time,
                type = Sessions.AttendType.valueOf(result.type),
                progressPhase = result.progressPhase
            )
        })
    }

    override fun getSchedules(year: Int, month: Int) = flow {
        emit(scheduleApi.getSchedules(year, month).toScheduleListModel())
    }
}
