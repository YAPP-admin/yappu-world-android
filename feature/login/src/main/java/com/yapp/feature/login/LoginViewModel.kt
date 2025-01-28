package com.yapp.feature.login

import androidx.lifecycle.ViewModel
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    val store: MviIntentStore<LoginState, LoginIntent, Nothing> =
        mviIntentStore(
            initialState = LoginState(),
            onIntent = { intent, _, reduce, _ ->
                when (intent) {
                    is LoginIntent.ClickLoginButton -> {}
                    is LoginIntent.ClickSignUpButton -> reduce { copy(showAgreementDialog = true) }
                    is LoginIntent.EmailChanged -> reduce { copy(email = intent.email) }
                    is LoginIntent.PasswordChanged -> reduce { copy(password = intent.password) }
                    is LoginIntent.CloseAgreementDialog -> reduce { copy(showAgreementDialog = false) }
                    is LoginIntent.CheckAgreement1 -> reduce { copy(agreement1 = intent.checked) }
                    is LoginIntent.CheckAgreement2 -> reduce { copy(agreement2 = intent.checked) }
                }
            }
        )
}

