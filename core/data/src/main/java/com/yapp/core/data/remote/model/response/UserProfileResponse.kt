package com.yapp.core.data.remote.model.response

import com.yapp.model.ActivityUnit
import com.yapp.model.UserInfo
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    val id: String,
    val name: String,
    val role: String,
    val activityUnits: List<UserActivityUnit>,
) {
    fun toModel() = UserInfo(
        id = id,
        name = name,
        role = role,
        activityUnits = activityUnits.map { it.toModel() }
    )
}

@Serializable
data class UserActivityUnit(
    val generation: Int,
    val position: ActivityUnitPosition,
) {
    fun toModel() = ActivityUnit(
        generation = generation,
        position = position.label
    )
}
