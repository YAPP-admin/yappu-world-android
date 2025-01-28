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
            onIntent = { intent, _, reduce, postSideEffect  ->
                when (intent) {
                    is LoginIntent.ClickLoginButton -> {}
                    is LoginIntent.ClickSignUpButton -> reduce { copy(showAgreementDialog = true) }
                    is LoginIntent.ChangeEmail -> reduce { copy(email = intent.email) }
                    is LoginIntent.ChangePassword -> reduce { copy(password = intent.password) }
                    is LoginIntent.CloseAgreementDialog -> reduce { copy(showAgreementDialog = false) }
                    is LoginIntent.CheckTerms -> reduce { copy(terms = intent.checked) }
                    is LoginIntent.CheckPersonalPolicy -> reduce { copy(personalPolicy = intent.checked) }
                    is LoginIntent.ClickNextButton -> postSideEffect(LoginSideEffect.NavigateToSignUp)
                }
            }
        )
}

