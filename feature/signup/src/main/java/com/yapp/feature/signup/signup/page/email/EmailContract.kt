package com.yapp.feature.signup.signup.page.email

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.yapp.feature.signup.R
import com.yapp.model.Regex

data class EmailState(
    val email: String = "",
) {
    val isEmailError =
        email.matches(Regex.email).not() && email.isNotEmpty()

    val emailErrorDescription: String?
        @Composable
        get() = if (isEmailError) stringResource(R.string.signup_screen_email_input_text_description) else null
}

sealed interface EmailIntent {
    data class ChangeEmail(val email: String) : EmailIntent
}

sealed interface EmailSideEffect {
    data class EmailChanged(val email: String) : EmailSideEffect
}
