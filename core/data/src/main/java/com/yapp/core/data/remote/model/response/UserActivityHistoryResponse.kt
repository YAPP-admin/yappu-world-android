package com.yapp.core.data.remote.model.response

import com.yapp.model.ActivityHistory
import kotlinx.serialization.Serializable

@Serializable
data class UserActivityHistoryResponse(
    val activityUnits: List<Unit>
) {
    @Serializable
    data class Unit(
        val generation: Int,
        val position: String,
        val activityStartDate: String?,
        val activityEndDate: String?
    )

    fun toModel(): ActivityHistory {
        return ActivityHistory(
            activityUnits = activityUnits.map {
                ActivityHistory.Unit(
                    generation = it.generation,
                    position = it.position,
                    activityStartDate = it.activityStartDate,
                    activityEndDate = it.activityEndDate
                )
            }
        )
    }
}
