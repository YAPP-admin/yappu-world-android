package com.yapp.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
internal interface PositionConfigDao {
    @Query("SELECT * FROM ${EntityTable.POSITION_CONFIG}")
    fun getPositionConfigs(): List<PositionConfigEntity>

    @Query("DELETE FROM ${EntityTable.POSITION_CONFIG}")
    fun deleteAllPositionConfigs()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPositionConfigs(positionConfigs: List<PositionConfigEntity>)

    @Transaction
    fun replacePositionConfigs(positionConfigs: List<PositionConfigEntity>) {
        deleteAllPositionConfigs()
        insertPositionConfigs(positionConfigs)
    }
}
