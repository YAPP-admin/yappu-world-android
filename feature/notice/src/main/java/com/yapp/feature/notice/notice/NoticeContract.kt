package com.yapp.feature.notice.notice

data class NoticeState(
    val notices: List<String> = emptyList(),
)

sealed interface NoticeIntent {
    data object ClickBackButton : NoticeIntent
    data class ClickNoticeItem(val noticeId: String) : NoticeIntent
}

sealed interface NoticeSideEffect {
    data class NavigateToNoticeDetail(val noticeId: String) : NoticeSideEffect
}
