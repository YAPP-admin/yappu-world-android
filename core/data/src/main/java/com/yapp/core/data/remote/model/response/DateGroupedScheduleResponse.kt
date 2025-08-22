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
    val startDayOfTheWeek: String,
    val endDate: String,
    val endDayOfTheWeek: String,
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
        startDayOfWeek = startDayOfTheWeek,
        endDayOfWeek = endDayOfTheWeek,
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

/**
 * v1/active-generation/sessions 전용
 *
 * 서버 응답의 progressPhase 문자열을 [ScheduleProgressPhase] 로 매핑한다.
 * 다른 API의 progressPhase 값과는 불일치할 수 있으므로 혼용하지 말 것.
 */
fun String?.toActiveSessionProgressPhase(): ScheduleProgressPhase {
    return when (this) {
        "DONE" -> ScheduleProgressPhase.DONE
        "ONGOING" -> ScheduleProgressPhase.ONGOING
        "TODAY" -> ScheduleProgressPhase.TODAY
        "PENDING" -> ScheduleProgressPhase.PENDING
        else -> ScheduleProgressPhase.PENDING
    }
}

/**
 * v1/active-generation/sessions 전용
 *
 * 서버 응답의 attendanceStatus 문자열을 [AttendanceStatus] 로 매핑한다.
 * 다른 API의 attendanceStatus 값과는 다를 수 있으므로 혼용하지 말 것.
 */
fun String?.toActiveSessionAttendanceStatus(): AttendanceStatus? {
    return when (this) {
        "PENDING" -> null
        "ON_TIME" -> AttendanceStatus.ATTENDED
        "LATE" -> AttendanceStatus.LATE
        "ABSENT" -> AttendanceStatus.ABSENT
        "EARLY_CHECK_OUT" -> AttendanceStatus.EARLY_LEAVE
        "EXCUSED_ABSENCE" -> AttendanceStatus.EXCUSED
        else -> null
    }
}