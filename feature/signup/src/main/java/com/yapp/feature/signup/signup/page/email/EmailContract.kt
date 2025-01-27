package com.yapp.feature.signup.signup.page.email

data class EmailState(
    val email: String = "",
)

sealed interface EmailIntent {
    data class ChangeEmail(val email: String) : EmailIntent
}

sealed interface EmailSideEffect {
    data class EmailChanged(val email: String) : EmailSideEffect
}
