package com.yapp.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PositionConfigDao {
    @Query("SELECT * FROM ${EntityTable.POSITION_CONFIG}")
    fun getPositionConfigs(): List<PositionConfigEntity>

    @Upsert
    fun upsertPositionConfigs(positionConfigs: List<PositionConfigEntity>)
}
