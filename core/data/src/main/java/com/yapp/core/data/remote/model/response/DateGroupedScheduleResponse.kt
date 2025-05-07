package com.yapp.core.data.remote.model.response

import com.yapp.model.AttendanceStatus
import com.yapp.model.DateGroupedSchedule
import com.yapp.model.ScheduleInfo
import com.yapp.model.ScheduleList
import com.yapp.model.ScheduleProgressPhase
import com.yapp.model.ScheduleType
import com.yapp.model.SessionType
import kotlinx.serialization.Serializable

@Serializable
data class DateGroupedScheduleResponse(
    val dates: List<ScheduleListResponse>
) {
    fun toScheduleListModel() = ScheduleList(
        dates
            .filter { it.schedules.isNotEmpty() }
            .map { it.toScheduleListModel() }
    )
}

@Serializable
data class ScheduleListResponse(
    val date: String,
    val isToday: Boolean,
    val dayOfTheWeek: String,
    val schedules: List<ScheduleResponse>,
) {
    fun toScheduleListModel() = DateGroupedSchedule(
        date = date,
        isToday = isToday,
        dayOfTheWeek = dayOfTheWeek,
        schedules = schedules.map { it.toScheduleModel() }
    )
}

@Serializable
data class ScheduleResponse(
    val id: String,
    val name: String,
    val place: String?,
    val date: String,
    val endDate: String?,
    val time: String?,
    val endTime: String?,
    val scheduleType: String,
    val sessionType: String?,
    val scheduleProgressPhase: String,
    val attendanceStatus: String?,
) {
    fun toScheduleModel() = ScheduleInfo(
        id = id,
        name = name,
        place = place,
        date = date,
        endDate = endDate,
        time = time,
        endTime = endTime,
        scheduleType = scheduleType.toScheduleType(),
        sessionType = sessionType.toSessionType(),
        scheduleProgressPhase = scheduleProgressPhase.toScheduleProgressPhase(),
        attendanceStatus = attendanceStatus.toAttendanceStatus(),
    )
}

fun String.toScheduleType() =
    ScheduleType.entries.firstOrNull { it.name == this } ?: ScheduleType.ETC

fun String?.toSessionType() =
    SessionType.entries.firstOrNull { it.name == this }

fun String?.toScheduleProgressPhase() =
    ScheduleProgressPhase.entries.firstOrNull { it.name == this }
        ?: ScheduleProgressPhase.PENDING

fun String?.toAttendanceStatus() =
    AttendanceStatus.entries.firstOrNull { it.label == this }