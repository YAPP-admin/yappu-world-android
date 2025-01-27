package com.yapp.feature.signup.signup

data class SignUpState(
    val currentStep: SignUpStep = SignUpStep.Name,
    val primaryButtonEnable: Boolean = false,
)

sealed interface SignUpIntent {
    data object ClickPrimaryButton : SignUpIntent
    data object ClickBackButton : SignUpIntent
    data object BackPressed : SignUpIntent
    data class UpdateName(val name: String) : SignUpIntent
    data class UpdateEmail(val email: String) : SignUpIntent
}

enum class SignUpStep {
    Name, Email, Password, Position, Complete, Pending
}
