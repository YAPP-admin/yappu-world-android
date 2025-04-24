package com.yapp.model

data class Sessions(
    val id: String,
    val name: String,
    val place: String?,
    val date: String,
    val endDate: String?,
    val time: String?,
    val type: AttendType,
    val progressPhase: String
) {
    enum class AttendType {
        OFFLINE, TEAM
    }
}
