package com.yapp.feature.login


data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailErrorDescription : String? = null,
    val passwordErrorDescription : String? = null
){
    val enableLoginButton : Boolean = email.isNotEmpty() && password.isNotEmpty()
}

sealed interface LoginIntent {
    data class EmailChanged (val email : String) : LoginIntent
    data class PasswordChanged (val password : String ) : LoginIntent
    data object ClickLoginButton : LoginIntent
    data object ClickSignUpButton : LoginIntent
}

sealed interface LoginSideEffect {
    data object ShowSignUpDialog : LoginSideEffect
    data class ShowToast(val message: String) : LoginSideEffect
}