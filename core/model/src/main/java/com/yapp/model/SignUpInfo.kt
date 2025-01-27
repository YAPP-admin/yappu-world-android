package com.yapp.model

data class SignUpInfo(
    val email: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
    val name: String = "",
    val activityUnits: List<ActivityUnitModel> = emptyList(),
    val signUpCode: String = ""
) {
    val isAllPasswordConditionValid =
        password.matches(passwordRegex) && password == passwordConfirm
}

data class ActivityUnitModel(
    val generation: Int,
    val position: String
)