package com.yapp.feature.notice.noticedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.PostsRepository
import com.yapp.model.exceptions.InvalidTokenException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
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
                loadNoticeData(intent.noticeId, reduce, postSideEffect)
            }
        }
    }

    private fun loadNoticeData(
        noticeId: String,
        reduce: (NoticeDetailState.() -> NoticeDetailState) -> Unit,
        postSideEffect: (NoticeDetailSideEffect) -> Unit,
    ) {
        reduce { copy(isLoadingNotice = true) }
        postsRepository.getNoticeItem(noticeId)
            .onEach { noticeItem ->
                reduce { copy(notice = noticeItem) }
            }
            .catch {
                when (it) {
                    is InvalidTokenException -> {
                        postSideEffect(NoticeDetailSideEffect.NavigateToLogin)
                    }
                    else -> {
                        postSideEffect(NoticeDetailSideEffect.HandleException(it))
                        it.record()
                    }
                }
            }
            .onCompletion {
                reduce { copy(isLoadingNotice = false) }
            }
            .launchIn(viewModelScope)
    }
}
