package com.yapp.feature.login


data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailErrorDescription: String? = null,
    val passwordErrorDescription: String? = null,
    val showAgreementDialog: Boolean = false,
    val agreement1: Boolean = false,
    val agreement2: Boolean = false,
) {
    val enableLoginButton: Boolean = email.isNotEmpty() && password.isNotEmpty()
    val enableNextButton: Boolean = agreement1 && agreement2
}

sealed interface LoginIntent {
    data class EmailChanged(val email: String) : LoginIntent
    data class PasswordChanged(val password: String) : LoginIntent
    data object ClickLoginButton : LoginIntent
    data object ClickSignUpButton : LoginIntent
    data object CloseAgreementDialog : LoginIntent
    data class CheckAgreement1(val checked: Boolean) : LoginIntent
    data class CheckAgreement2(val checked: Boolean) : LoginIntent
}
