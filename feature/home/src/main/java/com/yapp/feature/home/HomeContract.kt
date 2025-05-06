package com.yapp.feature.home

import com.yapp.model.HomeSessionList
import com.yapp.model.UpcomingSessionInfo

data class HomeState(
    val isLoading: Boolean = true,
    val sessionList: HomeSessionList = HomeSessionList(
        sessions = emptyList(),
        upcomingSessionId = null
    ),
    val upcomingSession: UpcomingSessionInfo? = null,
    val showAttendCodeBottomSheet: Boolean = false,
    val attendanceCodeDigits: List<String> = List(4) { "" },
    val showAttendanceCodeError: Boolean = false,
) {
    val attendanceCode: String
        get() = attendanceCodeDigits.joinToString(separator = "")
    val inputCompleteButtonEnabled: Boolean
        get() = attendanceCodeDigits.all { it.isNotBlank() } && !showAttendanceCodeError
}

sealed interface HomeIntent {
    data object ClickRequestAttendCode : HomeIntent
    data object ClickDismissDialog : HomeIntent
    data object EnterHomeScreen : HomeIntent
    data object ClickShowAllSession : HomeIntent

    data object RefreshUpcomingSession : HomeIntent

    data class ChangeAttendanceCodeDigits(val code: List<String>) : HomeIntent
    data object ClickRequestAttendance : HomeIntent

}

sealed interface HomeSideEffect {
    data object NavigateToSchedule : HomeSideEffect
    data object NavigateToLogin : HomeSideEffect
    data class ShowToast(val message: String) : HomeSideEffect
}
