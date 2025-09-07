package com.yapp.feature.session

import androidx.lifecycle.ViewModel
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class SessionViewModel @Inject constructor(
) : ViewModel() {

    val store: MviIntentStore<SessionState, SessionIntent, SessionSideEffect> =
        mviIntentStore(
            initialState = SessionState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: SessionIntent,
        state: SessionState,
        reduce: (SessionState.() -> SessionState) -> Unit,
        postSideEffect: (SessionSideEffect) -> Unit,
    ) {
        when (intent) {
            SessionIntent.EnterSessionScreen -> {
                // Initial load placeholder
                reduce { copy(isLoading = false) }
            }
            SessionIntent.Refresh -> {
                reduce { copy(isLoading = true) }
                // TODO: load data
                reduce { copy(isLoading = false) }
            }
        }
    }
}

