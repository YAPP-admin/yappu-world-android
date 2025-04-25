package com.yapp.model

data class ActivityHistory(
    val activityUnits: List<Unit>
) {
    data class Unit(
        val generation: Int,
        val position: String,
        val activityStartDate: String?,
        val activityEndDate: String?
    )
}
