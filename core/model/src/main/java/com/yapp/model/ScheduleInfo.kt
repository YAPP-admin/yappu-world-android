package com.yapp.model

data class ScheduleList(
    val dates: List<DateGroupedSchedule>
)

data class DateGroupedSchedule(
    val date: String,
    val isToday: Boolean,
    val dayOfTheWeek: String,
    val schedules: List<ScheduleInfo>
)

data class ScheduleInfo(
    val id: String,
    val name: String,
    val place: String?,
    val date: String,
    val endDate: String?,
    val time: String?,
    val endTime: String?,
    val scheduleType: ScheduleType,
    val sessionType: SessionType?,
    val scheduleProgressPhase: ScheduleProgressPhase,
    val attendanceStatus: AttendanceStatus?,
)

enum class ScheduleType {
    SESSION, TASK, ETC;
}

enum class SessionType {
    OFFLINE, ONLINE, TEAM;
}

enum class ScheduleProgressPhase(val title: String) {
    DONE("완료"), TODAY("당일"), ONGOING("예정"), PENDING("");
}

enum class AttendanceStatus(
    val label: String,
) {
    ATTENDED("출석"),
    LATE("지각"),
    ABSENT("결석"),
    EARLY_LEAVE("조퇴"),
    EXCUSED("공결");
}
