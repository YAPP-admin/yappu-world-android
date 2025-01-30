package com.yapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        PositionConfigEntity::class,
    ],
    version = 1,
)
internal abstract class RoomDataBase : RoomDatabase() {
    abstract fun positionConfigDao(): PositionConfigDao
}
