package com.yapp.core.data.remote.api

import com.yapp.core.data.remote.model.response.DateGroupedScheduleResponse
import com.yapp.core.data.remote.model.response.SessionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApi {
    @GET("v1/sessions")
    suspend fun getSessions(): SessionResponse

    @GET("v1/schedules")
    suspend fun getSchedules(
        @Query("year") year: Int,
        @Query("month") month: Int,
    ): DateGroupedScheduleResponse
}
