package com.yapp.feature.home

import com.yapp.model.HomeSessionList
import com.yapp.model.UpcomingSessionInfo

data class HomeState(
    val sessionList: HomeSessionList = HomeSessionList(
        sessions = emptyList(),
        upcomingSessionId = null
    ),
    val upcomingSession: UpcomingSessionInfo? = null,
    val showAttendCodeBottomSheet: Boolean = false
)

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
