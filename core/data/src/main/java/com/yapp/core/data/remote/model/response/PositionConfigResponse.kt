package com.yapp.core.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
internal data class PositionConfigResponse(
    val positions: List<PositionConfig>,
) {
    fun toProto() = positions.map { it.toProto() }
}

@Serializable
internal data class PositionConfig(
    val name: String,
    val label: String,
) {
    fun toProto(): com.yapp.core.data.PositionConfig = com.yapp.core.data.PositionConfig
        .newBuilder()
        .setName(name)
        .setLabel(label)
        .build()
}