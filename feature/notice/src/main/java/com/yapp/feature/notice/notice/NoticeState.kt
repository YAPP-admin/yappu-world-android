package com.yapp.feature.notice.notice

data class NoticeState(
    val notices: List<String> = emptyList(),
)

sealed interface NoticeIntent {
    data object ClickBackButton : NoticeIntent
}

sealed interface NoticeSideEffect {
}
