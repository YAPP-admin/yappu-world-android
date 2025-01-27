package com.yapp.feature.signup.signup.page.password

import androidx.lifecycle.ViewModel
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor() : ViewModel() {
    val store: MviIntentStore<PasswordState, PasswordIntent, PasswordSideEffect> =
        mviIntentStore(
            initialState = PasswordState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: PasswordIntent,
        state: PasswordState,
        reduce: (PasswordState.() -> PasswordState) -> Unit,
        postSideEffect: (PasswordSideEffect) -> Unit
    ) {
        when (intent) {
            is PasswordIntent.ChangePassword -> {
                reduce { copy(password = intent.password) }
                postSideEffect(PasswordSideEffect.PasswordChanged(intent.password))
            }
            is PasswordIntent.ChangePasswordConfirm -> {
                reduce { copy(passwordConfirm = intent.passwordConfirm) }
                postSideEffect(PasswordSideEffect.PasswordConfirmChanged(intent.passwordConfirm))
            }
        }
    }
}
