package com.yapp.feature.signup.signup.page.email

import androidx.lifecycle.ViewModel
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor() : ViewModel() {
    val store: MviIntentStore<EmailState, EmailIntent, EmailSideEffect> =
        mviIntentStore(
            initialState = EmailState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: EmailIntent,
        state: EmailState,
        reduce: (EmailState.() -> EmailState) -> Unit,
        postSideEffect: (EmailSideEffect) -> Unit
    ) {
        when (intent) {
            is EmailIntent.ChangeEmail -> {
                reduce { copy(email = intent.email) }
                postSideEffect(EmailSideEffect.EmailChanged(intent.email))
            }
        }
    }
}
