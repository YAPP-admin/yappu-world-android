package com.yapp.core.data.remote.model.response

import com.yapp.model.AttendanceStatus
import com.yapp.model.DateGroupedSchedule
import com.yapp.model.ScheduleInfo
import com.yapp.model.ScheduleList
import com.yapp.model.ScheduleProgressPhase
import com.yapp.model.ScheduleType
import com.yapp.model.SessionType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DateGroupedScheduleResponse(
    @SerialName("dates")
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
    @SerialName("date")
    val date: String,

    @SerialName("isToday")
    val isToday: Boolean,

    @SerialName("dayOfTheWeek")
    val dayOfTheWeek: String,

    @SerialName("schedules")
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
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("place")
    val place: String?,

    @SerialName("date")
    val date: String,

    @SerialName("endDate")
    val endDate: String?,

    @SerialName("time")
    val time: String?,

    @SerialName("endTime")
    val endTime: String?,

    @SerialName("scheduleType")
    val scheduleType: String,

    @SerialName("sessionType")
    val sessionType: String?,

    @SerialName("scheduleProgressPhase")
    val scheduleProgressPhase: String,

    @SerialName("attendanceStatus")
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
        scheduleType = toScheduleType(scheduleType),
        sessionType = toSessionType(sessionType),
        scheduleProgressPhase = toScheduleProgressPhase(scheduleProgressPhase),
        attendanceStatus = toAttendanceStatus(attendanceStatus),
    )

    companion object {
        fun toScheduleType(value: String): ScheduleType =
            when (value) {
                "SESSION" -> ScheduleType.SESSION
                "TASK" -> ScheduleType.TASK
                else -> ScheduleType.ETC
            }

        fun toSessionType(value: String?): SessionType? =
            when (value) {
                "OFFLINE" -> SessionType.OFFLINE
                "ONLINE" -> SessionType.ONLINE
                "TEAM" -> SessionType.TEAM
                else -> null
            }

        fun toScheduleProgressPhase(value: String): ScheduleProgressPhase =
            when (value) {
                "DONE" -> ScheduleProgressPhase.DONE
                "TODAY" -> ScheduleProgressPhase.TODAY
                "ONGOING" -> ScheduleProgressPhase.ONGOING
                else -> ScheduleProgressPhase.PENDING
            }

        fun toAttendanceStatus(value: String?): AttendanceStatus =
            when (value) {
                "ATTENDED" -> AttendanceStatus.ATTENDED
                "LATE" -> AttendanceStatus.LATE
                "ABSENT" -> AttendanceStatus.ABSENT
                "EARLY_LEAVE" -> AttendanceStatus.EARLY_LEAVE
                "EXCUSED" -> AttendanceStatus.EXCUSED
                else -> AttendanceStatus.SCHEDULED
            }
    }
}