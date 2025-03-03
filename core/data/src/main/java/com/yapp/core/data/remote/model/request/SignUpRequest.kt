package com.yapp.core.data.remote.model.request

import com.yapp.core.data.PositionConfigs
import com.yapp.model.SignUpInfo
import kotlinx.serialization.Serializable

@Serializable
internal data class SignUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val activityUnits: List<ActivityUnit>,
    val signUpCode: String,
    val fcmToken: String,
    val deviceAlarmToggle: Boolean,
)

@Serializable
internal data class ActivityUnit(
    val generation: Int,
    val position: String
)

internal fun SignUpInfo.toData(
    positionConfigs: PositionConfigs,
    fcmToken: String,
    deviceAlarmToggle: Boolean
) = SignUpRequest(
    email = email.trim(),
    password = password.trim(),
    name = name.trim(),
    activityUnits = activityUnits.map { it.toData(positionConfigs) },
    signUpCode = signUpCode.trim(),
    fcmToken = fcmToken,
    deviceAlarmToggle = deviceAlarmToggle,
)

internal fun com.yapp.model.ActivityUnit.toData(positionConfigs: PositionConfigs) = ActivityUnit(
    generation = generation ?: throw IllegalArgumentException("Generation must not be null"),
    position = positionConfigs.configsList.find { it.label == position }?.name
        ?: throw IllegalArgumentException("Invalid position: must not be null or undefined"),
)