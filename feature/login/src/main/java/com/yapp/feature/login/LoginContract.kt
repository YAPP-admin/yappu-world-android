package com.yapp.feature.login


data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailErrorDescription: String? = null,
    val passwordErrorDescription: String? = null,
    val showAgreementDialog: Boolean = false,
    val terms: Boolean = false,
    val personalPolicy: Boolean = false,
    val isLoginEnabled: Boolean = true,
) {
    val enableLoginButton: Boolean = email.isNotEmpty() && password.isNotEmpty() && isLoginEnabled
    val enableNextButton: Boolean = terms && personalPolicy
}

sealed interface LoginIntent {
    data class ChangeEmail(val email: String) : LoginIntent
    data class ChangePassword(val password: String) : LoginIntent
    data object ClickLoginButton : LoginIntent
    data object ClickSignUpButton : LoginIntent
    data object CloseAgreementDialog : LoginIntent
    data class CheckTerms(val checked: Boolean) : LoginIntent
    data class CheckPersonalPolicy(val checked: Boolean) : LoginIntent
    data object ClickNextButton : LoginIntent
    data object ClickTerms : LoginIntent
    data object ClickPersonalPolicy : LoginIntent
    data object EnterLoginScreen : LoginIntent
}

sealed interface LoginSideEffect {
    data object NavigateToSignUp : LoginSideEffect
    data object NavigateToHome : LoginSideEffect
    data object NavigateToSignUpPending : LoginSideEffect
    data object NavigateToSignUpReject : LoginSideEffect
    data class OpenWebBrowser(val link: String) : LoginSideEffect
    data object ShowUrlLoadFailToast : LoginSideEffect
    data class HandleException(val exception: Throwable) : LoginSideEffect
}