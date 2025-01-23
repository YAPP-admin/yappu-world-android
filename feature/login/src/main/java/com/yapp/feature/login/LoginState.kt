package com.yapp.feature.login


sealed interface LoginState {
    data object Loading : LoginState
    data object LoginSuccess : LoginState
    data object LoginError : LoginState
}

sealed interface LoginIntent {
    data class Login(val username: String, val password: String) : LoginIntent
    data object ShowSignUpDialog : LoginIntent
}

sealed interface LoginSideEffect {
    data object ShowSignUpDialog : LoginSideEffect
    data class ShowToast(val message: String) : LoginSideEffect
}