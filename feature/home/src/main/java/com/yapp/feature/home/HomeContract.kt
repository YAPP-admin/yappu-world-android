package com.yapp.feature.home

import com.yapp.core.ui.component.UserRole
import com.yapp.model.ActivityUnit
import com.yapp.model.NoticeList

data class HomeState(
    val name : String = "",
    val role : UserRole = UserRole.ACTIVE,
    val activityUnits : List<ActivityUnit> = listOf(ActivityUnit(position = "", generation = 0)),
    val noticeInfo: NoticeList = NoticeList(notices = listOf(), lastNoticeId = "", hasNext = false),
    val isLoading: Boolean = true,  // 전체 스켈레톤 여부
    val isUserInfoLoading: Boolean = true, // 사용자 정보 로딩 여부
    val isNoticesLoading: Boolean = true,  // 공지사항 로딩 여부
)

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
