package com.yapp.feature.session

import com.yapp.core.ui.util.formatSessionDateTime
import com.yapp.model.SessionDetailInfo

data class SessionState(
    val isLoading: Boolean = false,
    val sessionDetail: SessionDetailInfo? = null,
    val error: Throwable? = null
) {
    val sessionDateTime: String = sessionDetail?.let {
        formatSessionDateTime(
            startDate = it.startDate,
            startDayOfWeek = it.startDayOfWeek,
            startTime = it.startTime,
            endDate = it.endDate,
            endDayOfWeek = it.endDayOfWeek,
            endTime = it.endTime
        )
    } ?: ""
}

sealed interface SessionIntent {
    data object EnterSessionScreen : SessionIntent
    data object Refresh : SessionIntent
    data class ClickKakaoMap(val name: String, val latitude: Double, val longitude: Double) :
        SessionIntent

    data class ClickNaverMap(val latitude: Double, val longitude: Double, val name: String) :
        SessionIntent

    data class ClickCopyAddress(val address: String) : SessionIntent
    data class ClickNoticeItem(val noticeId: String) : SessionIntent
}

sealed interface SessionSideEffect {
    data class ShowToast(val message: String) : SessionSideEffect
    data class HandleException(val throwable: Throwable) : SessionSideEffect
    data object NavigateToLogin : SessionSideEffect
    data class NavigateToNoticeDetail(val noticeId: String) : SessionSideEffect
    data class OpenKakaoMap(val name: String, val latitude: Double, val longitude: Double) :
        SessionSideEffect

    data class OpenNaverMap(val latitude: Double, val longitude: Double, val name: String) :
        SessionSideEffect

    data class CopyAddressToClipboard(val address: String) : SessionSideEffect
}

