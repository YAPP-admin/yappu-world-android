package com.yapp.feature.signup.signup.page.name

data class NameState(
    val name: String = "",
)

sealed interface NameIntent {
    data class ChangeName(val name: String) : NameIntent
}

sealed interface NameSideEffect {
    data class NameChanged(val name: String) : NameSideEffect
}
