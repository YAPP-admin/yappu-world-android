package com.yapp.core.data.remote.model.request

import com.yapp.core.data.local.PositionConfigEntity
import com.yapp.model.SignUpInfo
import kotlinx.serialization.Serializable

@Serializable
internal data class SignUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val activityUnits: List<ActivityUnit>,
    val signUpCode: String
)

@Serializable
internal data class ActivityUnit(
    val generation: Int,
    val position: String
)

internal fun SignUpInfo.toData(positionConfigs: List<PositionConfigEntity>) = SignUpRequest(
    email = email.trim(),
    password = password.trim(),
    name = name.trim(),
    activityUnits = activityUnits.map { it.toData(positionConfigs) },
    signUpCode = signUpCode.trim()
)

internal fun com.yapp.model.ActivityUnit.toData(positionConfigs: List<PositionConfigEntity>) = ActivityUnit(
    generation = generation ?: throw IllegalArgumentException("Generation must not be null"),
    position = positionConfigs.find { it.label == position }?.name ?: throw IllegalArgumentException("Invalid position: must not be null or undefined"),
)