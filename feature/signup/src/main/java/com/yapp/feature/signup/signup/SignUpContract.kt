package com.yapp.feature.signup.signup

import com.yapp.model.ActivityUnit

data class SignUpState(
    val currentStep: SignUpStep = SignUpStep.Name,
    val primaryButtonEnable: Boolean = false,
    val name: String = "",
    val showSignUpCodeBottomDialog: Boolean = false,
) {
    val showKeyboardAboveButton = currentStep != SignUpStep.Position
    val showSignUpScreenButton: Boolean = showSignUpCodeBottomDialog.not()
}

sealed interface SignUpIntent {
    data object ClickPrimaryButton : SignUpIntent
    data object ClickBackButton : SignUpIntent
    data object BackPressed : SignUpIntent
    data class NameChanged(val name: String) : SignUpIntent
    data class EmailChanged(val email: String) : SignUpIntent
    data class PasswordChanged(val password: String) : SignUpIntent
    data class PasswordConfirmChanged(val passwordConfirm: String) : SignUpIntent
    data class ActivityUnitsChanged(val activityUnits: List<ActivityUnit>) : SignUpIntent
    data object DismissSignUpCodeBottomDialog : SignUpIntent
}

enum class SignUpStep {
    Name, Email, Password, Position, Complete, Pending
}
