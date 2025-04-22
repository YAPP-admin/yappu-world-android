package com.yapp.dataapi

import com.yapp.model.ScheduleList
import com.yapp.model.Sessions
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getSessions(): Flow<List<Sessions>>

    fun getSchedules(year: Int, month: Int): Flow<ScheduleList>
}
