package com.yapp.feature.notice.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.PostsRepository
import com.yapp.model.NoticeType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val getNoticeListRepository: PostsRepository,

    ) : ViewModel() {
    val store: MviIntentStore<NoticeState, NoticeIntent, NoticeSideEffect> =
        mviIntentStore(
            initialState = NoticeState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: NoticeIntent,
        state: NoticeState,
        reduce: (NoticeState.() -> NoticeState) -> Unit,
        postSideEffect: (NoticeSideEffect) -> Unit,
    ) {
        when (intent) {
            NoticeIntent.ClickBackButton -> postSideEffect(NoticeSideEffect.NavigateToBack)
            NoticeIntent.EnterNoticeScreen -> loadNotices(state, reduce)
            is NoticeIntent.ClickNoticeItem -> postSideEffect(
                NoticeSideEffect.NavigateToNoticeDetail(intent.noticeId)
            )

            is NoticeIntent.ClickNoticeType -> {
                loadNoticeTypeList(reduce, intent.noticeType)
            }

            NoticeIntent.LoadMoreNoticeItem -> if (state.notices.hasNext) loadNotices(state, reduce)
        }
    }

    private fun loadNotices(
        state: NoticeState,
        reduce: (NoticeState.() -> NoticeState) -> Unit,
    ) = viewModelScope.launch {

        if (state.isNoticeEmpty) {
            reduce { copy(isNoticesLoading = true) }
        }

        getNoticeListRepository
            .getNoticeList(state.notices.lastNoticeId.ifEmpty { null }, 30, state.noticeType.apiValue)
            .collectLatest { noticeList ->
                reduce { copy(notices = noticeList.copy(notices = noticeList.notices + state.notices.notices), isNoticesLoading = false) }
            }
    }

    private fun loadNoticeTypeList(
        reduce: (NoticeState.() -> NoticeState) -> Unit,
        noticeType: NoticeType,
    ) = viewModelScope.launch{
        reduce { copy(noticeType = noticeType, isNoticesLoading = true) }
        getNoticeListRepository
            .getNoticeList(null, 30, noticeType.apiValue)
            .collectLatest { noticeList ->
                reduce { copy(notices = noticeList, isNoticesLoading = false) }
            }
    }
}
