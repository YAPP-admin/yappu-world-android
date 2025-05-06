package com.yapp.feature.notice.notice

import com.yapp.model.NoticeList
import com.yapp.model.NoticeType

data class NoticeState(
    val notices: NoticeList = NoticeList(notices = listOf(), lastNoticeId = "", hasNext = false),
    val isNoticesLoading: Boolean = true,
    val noticeType : NoticeType = NoticeType.ALL
){
    val isSession = noticeType == NoticeType.SESSION
    val isAll = noticeType == NoticeType.ALL
    val isOperation = noticeType == NoticeType.OPERATION

    val isNoticeEmpty = notices.notices.isEmpty()
}

sealed interface NoticeIntent {
    data object EnterNoticeScreen : NoticeIntent
    data object ClickBackButton : NoticeIntent
    data class ClickNoticeType(val noticeType: NoticeType) : NoticeIntent
    data class ClickNoticeItem(val noticeId: String) : NoticeIntent
    data object LoadMoreNoticeItem : NoticeIntent
}

sealed interface NoticeSideEffect {
    data class NavigateToNoticeDetail(val noticeId: String) : NoticeSideEffect
    data object NavigateToBack : NoticeSideEffect
    data object NavigateToLogin : NoticeSideEffect
    data class HandleException(val exception: Throwable) : NoticeSideEffect
}
