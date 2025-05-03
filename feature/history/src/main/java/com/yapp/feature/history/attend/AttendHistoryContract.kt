package com.yapp.feature.history.attend

import com.yapp.model.ScheduleInfo

data class AttendHistoryState(
    val totalSessionCount: Int = 0,
    val remainingSessionCount: Int = 0,
    val sessionProgressRate: Int = 0,
    val attendancePoint: Int = 0,
    val attendanceCount: Int = 0,
    val lateCount: Int = 0,
    val absenceCount: Int = 0,
    val latePassCount: Int = 0,
    val sessions: List<ScheduleInfo> = emptyList()
) {
    val alertMessage: String
        get() {
            return if (absenceCount >= 10) {
                "결석이 10번 이상이 되면 출결에 문제가 생겨 재적처리가 될 수 있어요. 출석 관리에 조금 더 신경 써 주세요!"
            } else {
                ""
            }
        }
}

sealed interface AttendHistoryIntent {
    data object OnEntryScreen : AttendHistoryIntent
    data object OnClickBackButton : AttendHistoryIntent
}

sealed interface AttendHistorySideEffect {
    data object NavigateToBack : AttendHistorySideEffect
}
