package com.yapp.feature.signup.signup.page.name

import androidx.lifecycle.ViewModel
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor() : ViewModel() {
    val store: MviIntentStore<NameState, NameIntent, NameSideEffect> =
        mviIntentStore(
            initialState = NameState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: NameIntent,
        state: NameState,
        reduce: (NameState.() -> NameState) -> Unit,
        postSideEffect: (NameSideEffect) -> Unit
    ) {
        when (intent) {
            is NameIntent.ChangeName -> {
                reduce { copy(name = intent.name) }
                postSideEffect(NameSideEffect.ChangeName(intent.name))
            }
        }
    }
}
