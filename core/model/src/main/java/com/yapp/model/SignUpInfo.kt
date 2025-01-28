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

    val isActivityUnitsValid = activityUnits.none { it.position.isNullOrEmpty() || it.generation == null } && activityUnits.isNotEmpty()
}

@Stable
data class ActivityUnit(
    val generation: Int? = null,
    val position: String? = null,
) {
    operator fun plus(previousActivityUnit: List<ActivityUnit>): List<ActivityUnit> {
        return previousActivityUnit.toMutableList().apply { add(0, this@ActivityUnit) }
    }
}