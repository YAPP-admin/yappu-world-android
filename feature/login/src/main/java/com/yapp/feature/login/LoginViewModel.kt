package com.yapp.feature.login

import androidx.lifecycle.ViewModel
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    val store: MviIntentStore<LoginState, LoginIntent, LoginSideEffect> =
        mviIntentStore(
            initialState = LoginState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: LoginIntent,
        state: LoginState,
        reduce: (LoginState.() -> LoginState) -> Unit,
        postSideEffect: (LoginSideEffect) -> Unit,
    ) {
        when (intent) {
            is LoginIntent.ClickLoginButton -> {}
            is LoginIntent.ClickSignUpButton -> reduce { copy(showAgreementDialog = true) }
            is LoginIntent.ChangeEmail -> reduce { copy(email = intent.email) }
            is LoginIntent.ChangePassword -> reduce { copy(password = intent.password) }
            is LoginIntent.CloseAgreementDialog -> reduce {
                copy(
                    showAgreementDialog = false,
                    personalPolicy = false,
                    terms = false,
                )
            }
            is LoginIntent.CheckTerms -> reduce { copy(terms = intent.checked) }
            is LoginIntent.CheckPersonalPolicy -> reduce { copy(personalPolicy = intent.checked) }
            is LoginIntent.ClickNextButton -> {
                reduce {
                    copy(
                        showAgreementDialog = false,
                        personalPolicy = false,
                        terms = false,
                    )
                }
                postSideEffect(LoginSideEffect.NavigateToSignUp)
            }
            LoginIntent.ClickTerms -> postSideEffect(LoginSideEffect.ShowTerms)
            LoginIntent.ClickPersonalPolicy -> postSideEffect(LoginSideEffect.ShowPersonalPolicy)

        }
    }
}

