package com.yapp.feature.notice.noticedetail

import com.yapp.model.NoticeInfo
import com.yapp.model.NoticeType

data class NoticeDetailState(
    val notice: NoticeInfo = NoticeInfo(
        id = "",
        writerName = "",
        writerId = "",
        writerPosition = "",
        writerGeneration = 1,
        createdAt = "",
        title = "",
        content = "",
        noticeType = NoticeType.ALL
    ),
    val isLoadingNotice: Boolean = false
)

sealed interface NoticeDetailIntent {
    data object ClickBackButton : NoticeDetailIntent
    data class EnterScreen(val noticeId: String) : NoticeDetailIntent
}

sealed interface NoticeDetailSideEffect {
    data object NavigateToBack : NoticeDetailSideEffect
    data object NavigateToLogin : NoticeDetailSideEffect
    data class HandleException(val exception: Throwable) : NoticeDetailSideEffect
}
