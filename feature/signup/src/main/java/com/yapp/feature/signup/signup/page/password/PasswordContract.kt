package com.yapp.feature.signup.signup.page.password

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.yapp.feature.signup.R
import com.yapp.model.Regex

data class PasswordState(
    val password: String = "",
    val passwordConfirm: String = "",
) {
    val isPasswordError =
        password.matches(Regex.password).not() && password.isNotEmpty()

    val isPasswordConfirmError = (password != passwordConfirm) && passwordConfirm.isNotEmpty()

    val passwordErrorDescription: String?
        @Composable
        get() = if (isPasswordError) stringResource(R.string.signup_screen_password_input_text_description) else null

    val passwordConfirmErrorDescription: String?
        @Composable
        get() = if (isPasswordConfirmError) stringResource(R.string.signup_screen_password_confirm_input_text_description) else null
}

sealed interface PasswordIntent {
    data class ChangePassword(val password: String) : PasswordIntent
    data class ChangePasswordConfirm(val passwordConfirm: String) : PasswordIntent
}

sealed interface PasswordSideEffect {
    data class PasswordChanged(val password: String) : PasswordSideEffect
    data class PasswordConfirmChanged(val passwordConfirm: String) : PasswordSideEffect
}
