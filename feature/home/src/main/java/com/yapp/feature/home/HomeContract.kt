package com.yapp.feature.home

import com.yapp.model.ScheduleProgressPhase

data class HomeState(
    val sessions: List<Session> = emptyList(),
    val upcomingSessionId: String = ""
) {
    data class Session(
        val id: String,
        val title: String,
        val date: String,
        val place: String,
        val startTime: String,
        val endTime: String,
        val startDayOfWeek: String,
        val progressPhase: ScheduleProgressPhase
    )
}

sealed interface HomeIntent {
    data object ClickMoreButton : HomeIntent
    data object ClickSettingButton : HomeIntent
    data object EnterHomeScreen : HomeIntent
    data class ClickNoticeItem(val noticeId: String) : HomeIntent
}

sealed interface HomeSideEffect {
    data object NavigateToNotice : HomeSideEffect
    data class NavigateToNoticeDetail(val noticeId: String) : HomeSideEffect
    data object NavigateToSetting : HomeSideEffect
    data object NavigateToLogin : HomeSideEffect
    data class ShowToast(val message: String) : HomeSideEffect
}
