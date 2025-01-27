package com.yapp.model

data class SignUpInfo(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val activityUnits: List<ActivityUnitModel> = emptyList(),
    val signUpCode: String = ""
)

data class ActivityUnitModel(
    val generation: Int,
    val position: String
)

data class YappJWT(
    val accessToken: String,
    val refreshToken: String,
)