package com.yapp.dataapi

import com.yapp.model.Sessions
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun getSessions(): Flow<List<Sessions>>
}
