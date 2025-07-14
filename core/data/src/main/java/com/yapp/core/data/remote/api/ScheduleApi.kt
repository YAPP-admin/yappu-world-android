package com.yapp.core.data.remote.api

import com.yapp.core.data.remote.model.response.DateGroupedScheduleResponse
import com.yapp.core.data.remote.model.response.SessionResponse
import com.yapp.core.data.remote.model.response.UpcomingSessionAttendanceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApi {
    @GET("v2/sessions")
    suspend fun getSessions(
        @Query("generation") generation: Int? = null,
        @Query("start") start: String? = null,
        @Query("end") end: String? = null
    ): SessionResponse

    @GET("v1/sessions/upcoming")
    suspend fun getUpcomingSession(): UpcomingSessionAttendanceResponse

    @GET("v1/schedules")
    suspend fun getSchedules(
        @Query("year") year: Int,
        @Query("month") month: Int,
    ): DateGroupedScheduleResponse
}
