package com.yapp.core.data.remote.model.request

import com.yapp.model.SignUpInfo
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val activityUnits: List<ActivityUnit>,
    val signUpCode: String
)

@Serializable
data class ActivityUnit(
    val generation: Int,
    val position: String
)

fun SignUpInfo.toData() = SignUpRequest(
    email = email.trim(),
    password = password.trim(),
    name = name.trim(),
    activityUnits = activityUnits.map { it.toData() },
    signUpCode = signUpCode.trim()
)

fun com.yapp.model.ActivityUnit.toData() = ActivityUnit(
    // Data Layer의 ActivityUnit으로 변환 시, null 값이 들어가면 안됨. 잘못된 값이 전송되는 것을 막기 위해 !! 사용
    generation = generation!!,
    position = position!!,
)