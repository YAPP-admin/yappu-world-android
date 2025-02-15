package com.yapp.core.data.remote.api

import com.yapp.core.data.remote.model.request.DeviceAlarmRequest
import com.yapp.core.data.remote.model.request.FcmTokenRequest
import com.yapp.core.data.remote.model.response.GetMasterAlarmStatusResponse
import com.yapp.core.data.remote.model.response.PatchMasterAlarmStatusResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT

internal interface AlarmApi {
    @PUT("v1/users/fcm")
    suspend fun putFcmToken(
        @Body request: FcmTokenRequest,
    )

    @PUT("v1/alarms/device")
    suspend fun putDeviceAlarmStatus(
        @Body request: DeviceAlarmRequest,
    )

    @PATCH("v1/alarms/master")
    suspend fun patchMasterAlarmStatus(): PatchMasterAlarmStatusResponse

    @GET("v1/alarms")
    suspend fun getMasterAlarmStatus(): GetMasterAlarmStatusResponse
}