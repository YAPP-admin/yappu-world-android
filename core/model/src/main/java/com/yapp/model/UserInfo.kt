package com.yapp.model

import androidx.compose.runtime.Stable

@Stable
data class UserInfo(
    val id: String,
    val name: String,
    val role: String,
    val activityUnits: List<ActivityUnit>
)
