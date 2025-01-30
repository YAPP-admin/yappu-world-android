package com.yapp.core.data.remote.model.response

import com.yapp.core.data.local.PositionConfigEntity

internal data class PositionConfigResponse(
    val name: String,
    val label: String,
) {
    fun toEntity() = PositionConfigEntity(
        name = name,
        label = label,
    )
}