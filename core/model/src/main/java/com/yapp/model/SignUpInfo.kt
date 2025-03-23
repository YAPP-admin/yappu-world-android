package com.yapp.model

import androidx.compose.runtime.Stable

@Stable
data class SignUpInfo(
    val email: String = "",
    val isEmailVerified: Boolean = false,
    val password: String = "",
    val passwordConfirm: String = "",
    val name: String = "",
    val activityUnits: List<ActivityUnit> = emptyList(),
    val signUpCode: String = "",
) {
    val isAllPasswordConditionValid =
        password.matches(Regex.password) && password == passwordConfirm

    val isActivityUnitsValid = activityUnits.none { it.position.isNullOrEmpty() || it.generation == null } && activityUnits.isNotEmpty()
}
