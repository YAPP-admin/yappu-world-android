package com.yapp.feature.home

import com.yapp.model.UpcomingSessionInfo

data class HomeState(
    val isLoading: Boolean = true,
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
    data class ClickSessionItem(val sessionId: String) : HomeIntent
    data object ClickShowAllSession : HomeIntent
    data object Refresh : HomeIntent
    data object ClickShowAllAttendanceHistory : HomeIntent
    data class ChangeAttendanceCodeDigits(val code: List<String>) : HomeIntent
    data object ClickRequestAttendance : HomeIntent
    data object ClickBasicRuleLink : HomeIntent
    data class ClickNotice(val id: String) : HomeIntent
    data class ClickDetail(val id: String) : HomeIntent
}

sealed interface HomeSideEffect {
    data object NavigateToSchedule : HomeSideEffect
    data object NavigateToLogin : HomeSideEffect
    data object NavigateToAttendanceHistory : HomeSideEffect
    data class NavigateToSessionDetail(val sessionId: String) : HomeSideEffect
    data class ShowToast(val message: String) : HomeSideEffect
    data class HandleException(val exception: Throwable) : HomeSideEffect
    data class NavigateToNotice(val id: String) : HomeSideEffect
    data class OpenUrl(val url: String) : HomeSideEffect
}
