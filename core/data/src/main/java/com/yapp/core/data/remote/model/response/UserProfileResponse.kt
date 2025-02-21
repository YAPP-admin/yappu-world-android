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
    fun toUserInfo() = UserInfo(
        id = id,
        name = name,
        role = role,
        activityUnits = activityUnits.map { it.toUserActivityUnit() }
    )
}


@Serializable
data class UserActivityUnit(
    val generation: Int,
    val position: String,
) {
    fun toUserActivityUnit() = ActivityUnit(
        generation = generation,
        position = position
    )

}
