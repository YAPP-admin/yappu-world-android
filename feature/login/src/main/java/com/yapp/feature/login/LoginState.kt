package com.yapp.feature.login


data class LoginState(
    val id: String = "",
    val password: String = "",
    val idFailDescription : String? = null,
    val pwFailDescription : String? = null,
    val isPWVisible: Boolean = false, // 비밀번호 표시 여부
){
    val isActivateLoginButton : Boolean = id.isNotEmpty() && password.isNotEmpty()
}

sealed interface LoginIntent {
    data class InputID (val id : String) : LoginIntent
    data class InputPW ( val pw : String ) : LoginIntent
    data object ClickLoginButton : LoginIntent
    data object ClickSignUpButton : LoginIntent
}

sealed interface LoginSideEffect {
    data object ShowSignUpDialog : LoginSideEffect
    data class ShowToast(val message: String) : LoginSideEffect
}