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
    email = email,
    password = password,
    name = name,
    activityUnits = activityUnits.map { it.toData() },
    signUpCode = signUpCode
)

fun com.yapp.model.ActivityUnit.toData() = ActivityUnit(
    generation = generation,
    position = position,
)