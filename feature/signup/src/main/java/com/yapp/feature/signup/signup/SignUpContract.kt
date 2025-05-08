package com.yapp.feature.signup.signup

import com.yapp.feature.signup.R
import com.yapp.model.ActivityUnit

data class SignUpState(
    val currentStep: SignUpStep = SignUpStep.Name,
    val primaryButtonEnable: Boolean = false,
    val name: String = "",
    val showSignUpCodeBottomDialog: Boolean = false,
    val signUpCode: String = "",
    val signUpErrorInputTextDescription: String? = null,
    val positions: List<String> = emptyList(),
) {
    val showKeyboardAboveButton = currentStep != SignUpStep.Position
    val showSignUpScreenButton: Boolean = showSignUpCodeBottomDialog.not()
    val inputCompleteButtonEnable = signUpCode.isNotBlank()
    val isSignUpErrorInputTextError = signUpErrorInputTextDescription != null
    val backIcon: Int? = when (currentStep) {
        SignUpStep.Complete -> null
        else -> com.yapp.core.designsystem.R.drawable.icon_chevron_left
    }

    val primaryButtonText = when (currentStep) {
        SignUpStep.Complete -> R.string.signup_screen_button_start
        else -> R.string.signup_screen_button_next
    }

    val showPendingButton = currentStep == SignUpStep.Pending
    val showRejectButton = currentStep == SignUpStep.Reject
}

sealed interface SignUpIntent {
    data object EnterScreen : SignUpIntent
    data object ClickPrimaryButton : SignUpIntent
    data object ClickBackButton : SignUpIntent
    data object BackPressed : SignUpIntent
    data class NameChanged(val name: String) : SignUpIntent
    data class EmailChanged(val email: String, val verified: Boolean) : SignUpIntent
    data class PasswordChanged(val password: String) : SignUpIntent
    data class PasswordConfirmChanged(val passwordConfirm: String) : SignUpIntent
    data class ActivityUnitsChanged(val activityUnits: List<ActivityUnit>) : SignUpIntent
    data object DismissSignUpCodeBottomDialog : SignUpIntent
    data object ClickNoSignUpCodeButton : SignUpIntent
    data object ClickInputCompleteButton : SignUpIntent
    data object ClickPendingButton : SignUpIntent
    data class ChangeSignUpCode(val signUpCode: String) : SignUpIntent
    data class HandleException(val exception: Throwable) : SignUpIntent
}

sealed interface SignUpSideEffect {
    data object NavigateBack : SignUpSideEffect
    data object NavigateHome : SignUpSideEffect
    data object ClearFocus : SignUpSideEffect
    data class OpenWebBrowser(val link: String) : SignUpSideEffect
    data object ShowUrlLoadFailToast : SignUpSideEffect
    data class HandleException(val exception: Throwable) : SignUpSideEffect
}

enum class SignUpStep {
    Name, Email, Password, Position, Complete, Pending, Reject;

    companion object {
        fun from(value: String?): SignUpStep {
            return try {
                value?.let { valueOf(it) } ?: Name
            } catch (e: Exception) {
                Name
            }
        }
    }
}
