package com.yapp.core.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class Attendance(
    val sessionId: String,
    val attendanceCode: String
)
