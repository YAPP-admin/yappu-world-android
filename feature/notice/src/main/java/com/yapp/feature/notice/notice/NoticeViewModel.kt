package com.yapp.feature.notice.notice

import androidx.lifecycle.ViewModel
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor() : ViewModel() {
    val store: MviIntentStore<NoticeState, NoticeIntent, NoticeSideEffect> =
        mviIntentStore(
            initialState = NoticeState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: NoticeIntent,
        state: NoticeState,
        reduce: (NoticeState.() -> NoticeState) -> Unit,
        postSideEffect: (NoticeSideEffect) -> Unit
    ) {
        when (intent) {
            NoticeIntent.ClickBackButton -> TODO()
        }
    }
}
