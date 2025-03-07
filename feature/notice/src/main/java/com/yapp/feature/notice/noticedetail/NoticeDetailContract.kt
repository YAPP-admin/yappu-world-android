package com.yapp.feature.notice.noticedetail

data class NoticeDetailState(
    val dummyData: String = markdown1,
)

sealed interface NoticeDetailIntent {
    data object ClickBackButton : NoticeDetailIntent
    data object EnterScreen : NoticeDetailIntent
}

sealed interface NoticeDetailSideEffect
