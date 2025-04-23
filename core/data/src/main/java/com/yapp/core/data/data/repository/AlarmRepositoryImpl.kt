package com.yapp.core.data.data.repository

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.yapp.core.data.local.SecurityPreferences
import com.yapp.core.data.remote.api.AlarmApi
import com.yapp.core.data.remote.model.request.DeviceAlarmRequest
import com.yapp.core.data.remote.model.request.FcmTokenRequest
import com.yapp.dataapi.AlarmRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class AlarmRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val alarmApi: AlarmApi,
    private val securityPreferences: SecurityPreferences,
    ) : AlarmRepository {

    override suspend fun updateFcmToken(fcmToken: String) {
        alarmApi.putFcmToken(FcmTokenRequest(fcmToken))
    }

    override suspend fun updateDeviceAlarmStatus() {
        val deviceAlarmStatus = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED
        } else {
            true // Android 12 이하에서는 자동 허용
        }

        alarmApi.putDeviceAlarmStatus(
            request = DeviceAlarmRequest(deviceAlarmStatus)
        )
    }

    override suspend fun getMasterAlarmStatus(): Boolean {
        return alarmApi.getMasterAlarmStatus().isMasterEnabled
    }

    override suspend fun updateMasterAlarmStatus(): Boolean {
        return alarmApi.patchMasterAlarmStatus().isEnabled
    }
}