package com.yapp.feature.login

import androidx.lifecycle.ViewModel
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore


class LoginViewModel : ViewModel() {

    val store: MviIntentStore<LoginState, LoginIntent, LoginSideEffect> =
        mviIntentStore(
            initialState = LoginState.LoginSuccess,
            onIntent = { intent, state, reduce, postSideEffect ->
                when (intent) {
                    is LoginIntent.Login -> {
                        if (intent.username == "test" && intent.password == "password") {
                            postSideEffect(LoginSideEffect.ShowToast("로그인 버튼 클릭 \n ${intent.username} ${intent.password}"))
                        }
                    }
                    LoginIntent.ShowSignUpDialog -> {
                        postSideEffect(LoginSideEffect.ShowToast("회원가입 버튼 클릭"))
                    }
                }
            }
        )
}

