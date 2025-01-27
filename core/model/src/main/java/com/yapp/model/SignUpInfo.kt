package com.yapp.model

import androidx.compose.runtime.Stable

@Stable
data class SignUpInfo(
    val email: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
    val name: String = "",
    val activityUnits: List<ActivityUnit> = emptyList(),
    val signUpCode: String = ""
) {
    val isAllPasswordConditionValid =
        password.matches(passwordRegex) && password == passwordConfirm
}

@Stable
data class ActivityUnit(
    val generation: Int,
    val position: String
)