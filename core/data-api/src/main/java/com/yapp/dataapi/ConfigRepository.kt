package com.yapp.dataapi

import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    fun getPositionConfigs(): Flow<List<String>>
}