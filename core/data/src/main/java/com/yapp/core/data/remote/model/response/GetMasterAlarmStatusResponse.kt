package com.yapp.core.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GetMasterAlarmStatusResponse(
    val isMasterEnabled: Boolean,
)
