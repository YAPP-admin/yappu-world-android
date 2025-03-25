package com.yapp.feature.notice.noticedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.PostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeDetailViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
) : ViewModel() {
    val store: MviIntentStore<NoticeDetailState, NoticeDetailIntent, NoticeDetailSideEffect> =
        mviIntentStore(
            initialState = NoticeDetailState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: NoticeDetailIntent,
        state: NoticeDetailState,
        reduce: (NoticeDetailState.() -> NoticeDetailState) -> Unit,
        postSideEffect: (NoticeDetailSideEffect) -> Unit,
    ) {
        when (intent) {
            NoticeDetailIntent.ClickBackButton -> {
                postSideEffect(NoticeDetailSideEffect.NavigateToBack)
            }

            is NoticeDetailIntent.EnterScreen -> {
                loadNoticeData(intent.noticeId, state, reduce)
            }
        }
    }

    private fun loadNoticeData(
        noticeId: String,
        state: NoticeDetailState,
        reduce: (NoticeDetailState.() -> NoticeDetailState) -> Unit,
    ) = viewModelScope.launch {
        reduce { copy(isLoadingNotice = true) }
        postsRepository.getNoticeItem(noticeId).collectLatest { noticeItem ->
            reduce { copy(notice = noticeItem, isLoadingNotice = false) }
        }
    }
}
