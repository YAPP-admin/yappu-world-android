package com.yapp.feature.history.attend

import com.yapp.model.AttendanceHistoryList
import java.text.DecimalFormat

data class AttendHistoryState(
    val totalSessionCount: Int = 0,
    val remainingSessionCount: Int = 0,
    val sessionProgressRate: Double = 0.0,
    val attendancePoint: Int = 0,
    val attendanceCount: Int = 0,
    val lateCount: Int = 0,
    val absenceCount: Int = 0,
    val latePassCount: Int = 0,
    val attendanceHistoryList: AttendanceHistoryList = AttendanceHistoryList(
        histories = emptyList()
    ),
) {
    val formattedSessionProgressRate: String
        get() = if (sessionProgressRate % 1.0 == 0.0) {
            "${sessionProgressRate.toInt()}%"
        } else {
            "${DecimalFormat("#.#").format(sessionProgressRate)}%"
        }
}

sealed interface AttendHistoryIntent {
    data object OnEntryScreen : AttendHistoryIntent
    data object OnClickBackButton : AttendHistoryIntent
}

sealed interface AttendHistorySideEffect {
    data object NavigateToBack : AttendHistorySideEffect
}
