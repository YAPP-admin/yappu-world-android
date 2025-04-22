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

    companion object {
        fun fromApiValue(apiValue: String): ScheduleType {
            return (entries.find { it.name == apiValue }) ?: ETC
        }
    }
}

enum class SessionType {
    OFFLINE, ONLINE, TEAM;

    companion object {
        fun fromApiValue(apiValue: String): SessionType {
            return (entries.find { it.name == apiValue }) ?: OFFLINE
        }
    }
}

enum class ScheduleProgressPhase {
    DONE, TODAY, ONGOING, PENDING;

    companion object {
        fun fromApiValue(apiValue: String): ScheduleProgressPhase {
            return (entries.find { it.name == apiValue }) ?: PENDING
        }
    }
}

enum class AttendanceStatus {
    SCHEDULED, ATTENDED, LATE, ABSENT, EARLY_LEAVE, EXCUSED;

    companion object {
        fun fromApiValue(apiValue: String): AttendanceStatus {
            return (entries.find { it.name == apiValue }) ?: SCHEDULED
        }
    }
}
