package com.yapp.model

data class ScheduleList(
    val dates: List<DateGroupedSchedule>
)

data class DateGroupedSchedule(
    val date: String,
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
    val attendanceStatus: AttendanceStatus,
)

enum class ScheduleType {
    SESSION, TASK, ETC;
}

enum class SessionType {
    OFFLINE, ONLINE, TEAM;
}

enum class ScheduleProgressPhase {
    DONE, TODAY, ONGOING, PENDING;
}

enum class AttendanceStatus {
    SCHEDULED, ATTENDED, LATE, ABSENT, EARLY_LEAVE, EXCUSED;
}
