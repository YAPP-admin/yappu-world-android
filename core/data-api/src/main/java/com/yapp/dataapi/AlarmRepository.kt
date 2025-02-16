package com.yapp.dataapi

interface AlarmRepository {
    suspend fun updateFcmToken(fcmToken: String)
    suspend fun updateDeviceAlarmStatus()
    suspend fun getMasterAlarmStatus(): Boolean
    suspend fun updateMasterAlarmStatus(): Boolean
}