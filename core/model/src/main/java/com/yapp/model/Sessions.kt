package com.yapp.model

data class HomeSessionList(
    val sessions: List<HomeSession>,
    val upcomingSessionId: String?
)

data class HomeSession(
    val id: String,
    val name: String,
    val place: String,
    val date: String,
    val dayOfWeek: String,
    val relativeDays: Int,
    val startTime: String?,
    val endTime: String?,
    val progressPhase: SessionProgressPhase,
    val attendanceStatus: AttendanceStatus?
) {
    val showSessionChip: Boolean
        get() {
            return if (progressPhase != SessionProgressPhase.PENDING) {
                true
            } else {
                relativeDays < 0
            }
        }
}

enum class SessionProgressPhase(val title: String) {
    DONE("완료"),
    TODAY("당일"),
    UPCOMING("임박"),
    PENDING("예정");
}
