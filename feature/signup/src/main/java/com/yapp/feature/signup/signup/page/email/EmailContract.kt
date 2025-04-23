package com.yapp.feature.signup.signup.page.email

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.yapp.feature.signup.R
import com.yapp.model.Regex

data class EmailState(
    val email: String = "",
    val isEmailDuplicated: Boolean = false,
    val isEmailChecking: Boolean = false,
) {
    val isEmailRegexFailed = email.matches(Regex.email).not() && email.isNotEmpty()

    val isEmailError =
        isEmailRegexFailed || isEmailDuplicated

    val isEmailVerified = isEmailError.not() && isEmailChecking.not() && email.isNotEmpty()

    val emailDescription: String?
        @Composable
        get() = when {
            isEmailDuplicated -> {
                stringResource(R.string.signup_screen_email_input_duplicate_text_description)
            }
            isEmailRegexFailed -> {
                stringResource(R.string.signup_screen_email_input_error_text_description)
            }
            isEmailVerified -> {
                stringResource(R.string.signup_screen_email_input_verified_text_description)
            }
            else -> null
        }
}

sealed interface EmailIntent {
    data class ChangeEmail(val email: String) : EmailIntent
}

sealed interface EmailSideEffect {
    data class EmailChanged(val email: String, val verified: Boolean = false) : EmailSideEffect
}
