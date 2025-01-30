package com.yapp.core.data.remote.model.response

import com.yapp.core.data.local.PositionConfigEntity
import kotlinx.serialization.Serializable

@Serializable
internal data class PositionConfigResponse(
    val positions: List<PositionConfig>,
) {
    fun toEntity() = positions.map { PositionConfigEntity(name = it.name, label = it.label) }
}

@Serializable
internal data class PositionConfig(
    val name: String,
    val label: String,
)