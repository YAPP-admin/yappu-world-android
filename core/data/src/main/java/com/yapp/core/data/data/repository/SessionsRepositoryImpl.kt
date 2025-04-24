package com.yapp.core.data.data.repository

import com.yapp.core.data.remote.api.SessionApi
import com.yapp.dataapi.SessionRepository
import com.yapp.model.Sessions
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class SessionsRepositoryImpl @Inject constructor(
    private val sessionApi: SessionApi
): SessionRepository{
    override fun getSessions() = flow {
        emit(sessionApi.getSessions().sessions.map { result ->
            Sessions(
                id = result.id,
                name = result.name,
                place = result.place,
                date = result.date,
                endDate = result.endDate,
                time = result.time,
                type = Sessions.AttendType.valueOf(result.type),
                progressPhase = result.progressPhase
            )
        })
    }
}
