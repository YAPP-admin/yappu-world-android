package com.yapp.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EntityTable.POSITION_CONFIG)
data class PositionConfigEntity(
    @PrimaryKey
    val name: String,
    val label: String,
)
