package com.yapp.core.data.remote.api

import com.yapp.core.data.remote.model.response.AttendStatisticsResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface AttendanceApi {
    @POST("v1/attendances")
    suspend fun postAttendance()

    @GET("v1/attendances/statistics")
    suspend fun getAttendanceStatistics(): AttendStatisticsResponse
}
