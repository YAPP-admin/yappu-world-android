package com.yapp.feature.home

import com.yapp.model.ScheduleProgressPhase
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class HomeState(
    val sessions: List<Session> = emptyList(),
    val upcomingSessionId: String = "",
    val showAttendCodeBottomSheet: Boolean = false
) {
    val attendanceTitle: String
        get() {
            val today = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            val hasTodaySession = sessions.any { session ->
                runCatching { LocalDate.parse(session.date, formatter) }
                    .getOrNull() == today
            }

            return when {
                hasTodaySession -> {
                    "세션 당일이에요! 활기찬 하루 되세요 :)"
                }
                sessions.all { it.progressPhase == ScheduleProgressPhase.DONE } -> {
                    "다가오는 세션이 없어요."
                }
                else -> "다가오는 세션을 준비해볼까요?"
            }
        }

    val todayOrUpcomingSession: Session?
        get() {
            val today = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            val todaySession = sessions.firstOrNull { session ->
                runCatching { LocalDate.parse(session.date, formatter) }
                    .getOrNull() == today
            }

            if (todaySession != null) return todaySession

            return sessions
                .filter { it.progressPhase != ScheduleProgressPhase.DONE }
                .minByOrNull {
                    runCatching { LocalDate.parse(it.date, formatter) }
                        .getOrDefault(LocalDate.MAX)
                }
        }

    val attendState: AttendState
        get() {
            val session = todayOrUpcomingSession ?: return AttendState.NOT_YET
            if (session.isAttended) return AttendState.COMPLETED
            if (session.progressPhase == ScheduleProgressPhase.DONE) return AttendState.NOT_YET

            val now = LocalTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm")

            val startTime = runCatching { LocalTime.parse(session.startTime, formatter) }.getOrNull()
            val endTime = runCatching { LocalTime.parse(session.endTime, formatter) }.getOrNull()

            val attendOpenTime = startTime?.minusMinutes(20)

            return when {
                attendOpenTime == null || endTime == null -> AttendState.NOT_YET
                now.isBefore(attendOpenTime) -> AttendState.NOT_YET
                else -> AttendState.POSSIBLE
            }
        }

    val buttonTitle: String
        get() = when (attendState) {
            AttendState.NOT_YET -> "20분 전부터 출석이 가능해요"
            AttendState.POSSIBLE -> "출석하기"
            AttendState.COMPLETED -> "출석완료!"
        }

    enum class AttendState {
        NOT_YET,     // 아직 출석 가능 시간 전
        POSSIBLE,    // 출석 가능 (예: 세션 20분 전 ~ 종료 전)
        COMPLETED    // 출석 완료 상태 (또는 출석 후 상태)
    }

    data class Session(
        val id: String,
        val title: String,
        val date: String,
        val place: String,
        val startTime: String,
        val endTime: String,
        val startDayOfWeek: String,
        val progressPhase: ScheduleProgressPhase,
        val isAttended: Boolean = false
    )
}

sealed interface HomeIntent {
    data object ClickRequestAttendCode : HomeIntent
    data object ClickDismissDialog : HomeIntent
    data class ClickRequestAttendance(val code: String, val sessionId: String) : HomeIntent
    data object EnterHomeScreen : HomeIntent
    data object ClickShowAllSession : HomeIntent
}

sealed interface HomeSideEffect {
    data object NavigateToSchedule : HomeSideEffect
    data object NavigateToLogin : HomeSideEffect
    data class ShowToast(val message: String) : HomeSideEffect
}
