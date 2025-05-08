package com.yapp.feature.notice.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.PostsRepository
import com.yapp.model.NoticeType
import com.yapp.model.exceptions.InvalidTokenException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
            NoticeIntent.EnterNoticeScreen -> loadNotices(state, reduce)
            is NoticeIntent.ClickNoticeItem -> postSideEffect(
                NoticeSideEffect.NavigateToNoticeDetail(intent.noticeId)
            )

            is NoticeIntent.ClickNoticeType -> {
                loadNoticeTypeList(reduce, postSideEffect, intent.noticeType)
            }

            NoticeIntent.LoadMoreNoticeItem -> if (state.notices.hasNext) loadNotices(state, reduce)
        }
    }

    private fun loadNotices(
        state: NoticeState,
        reduce: (NoticeState.() -> NoticeState) -> Unit,
    ) {

        if (state.isNoticeEmpty) {
            reduce { copy(isNoticesLoading = true) }
        }

        getNoticeListRepository
            .getNoticeList(
                state.notices.lastNoticeId.ifEmpty { null },
                30,
                state.noticeType.apiValue
            )
            .onEach { noticeList ->
                reduce {
                    copy(
                        notices = noticeList.copy(notices = state.notices.notices + noticeList.notices),
                        isNoticesLoading = false
                    )
                }
            }
            .catch { it.record() }
            .launchIn(viewModelScope)
    }

    private fun loadNoticeTypeList(
        reduce: (NoticeState.() -> NoticeState) -> Unit,
        postSideEffect: (NoticeSideEffect) -> Unit,
        noticeType: NoticeType,
    ) {
        reduce { copy(noticeType = noticeType, isNoticesLoading = true) }
        getNoticeListRepository
            .getNoticeList(null, 30, noticeType.apiValue)
            .onEach { noticeList ->
                reduce { copy(notices = noticeList, isNoticesLoading = false) }
            }
            .catch {
                when (it) {
                    is InvalidTokenException -> {
                        postSideEffect(NoticeSideEffect.NavigateToLogin)
                    }
                    else -> {
                        postSideEffect(NoticeSideEffect.HandleException(it))
                        it.record()
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}
