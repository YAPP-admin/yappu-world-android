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
            onIntent = { intent, state, reduce, postSideEffect ->
                when (intent) {
                    is LoginIntent.ClickLoginButton -> {}
                    is LoginIntent.ClickSignUpButton -> { postSideEffect(LoginSideEffect.ShowToast("회원가입 버튼 클릭")) }
                    is LoginIntent.EmailChanged -> reduce { copy(email = intent.email) }
                    is LoginIntent.PasswordChanged -> reduce { copy(password = intent.password )}
                }
            }
        )
}

