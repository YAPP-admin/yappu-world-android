package com.yapp.core.data.remote.model.response

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

    @SerialName("schedules")
    val schedules: List<ScheduleResponse>,
) {
    fun toScheduleListModel() = DateGroupedSchedule(
        date = date,
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
        scheduleType = ScheduleType.fromApiValue(scheduleType),
        sessionType = sessionType?.let { SessionType.fromApiValue(it) },
        scheduleProgressPhase = ScheduleProgressPhase.fromApiValue(scheduleProgressPhase),
        attendanceStatus = attendanceStatus
    )
}