package com.yapp.core.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class DeviceAlarmRequest(
    val deviceToggle: String,
)
