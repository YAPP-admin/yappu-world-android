package com.yapp.core.data.remote.model.response

import com.yapp.core.data.remote.model.response.ScheduleResponse.Companion.toAttendanceStatus
import com.yapp.core.data.remote.model.response.ScheduleResponse.Companion.toScheduleProgressPhase
import com.yapp.core.data.remote.model.response.ScheduleResponse.Companion.toSessionType
import com.yapp.model.DateGroupedSchedule
import com.yapp.model.ScheduleInfo
import com.yapp.model.ScheduleType
import kotlinx.serialization.Serializable

@Serializable
data class SessionResponse(
    val sessions: List<Session>,
    val upcomingSessionId: String?
) {
    @Serializable
    data class Session(
        val id: String,
        val name: String,
        val place: String?,
        val date: String,
        val startDayOfWeek: String,
        val endDate: String?,
        val endDayOfWeek: String?,
        val relativeDays: Int,
        val time: String?,
        val endTime: String?,
        val type: String,
        val progressPhase: String,
        val attendanceStatus: String?
    )
}

fun List<SessionResponse.Session>.toDateGroupedScheduleList(): List<DateGroupedSchedule> {
    return groupBy { it.date }.map { (date, sessions) ->
        val firstSession = sessions.first()
        DateGroupedSchedule(
            date = date,
            isToday = firstSession.relativeDays == 0,
            dayOfTheWeek = firstSession.startDayOfWeek,
            schedules = sessions.map {
                ScheduleInfo(
                    id = it.id,
                    name = it.name,
                    place = it.place,
                    date = it.date,
                    endDate = it.endDate,
                    time = it.time,
                    endTime = it.endTime,
                    scheduleType = ScheduleType.SESSION,
                    sessionType = it.type.toSessionType(),
                    scheduleProgressPhase = it.progressPhase.toScheduleProgressPhase(),
                    attendanceStatus = it.attendanceStatus?.toAttendanceStatus()
                )
            }
        )
    }
}
