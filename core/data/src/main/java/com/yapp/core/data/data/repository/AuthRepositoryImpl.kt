package com.yapp.core.data.data.repository

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.datastore.core.DataStore
import com.google.firebase.messaging.FirebaseMessaging
import com.yapp.core.data.PositionConfigs
import com.yapp.core.data.local.SecurityPreferences
import com.yapp.core.data.remote.api.AlarmApi
import com.yapp.core.data.remote.api.AuthApi
import com.yapp.core.data.remote.model.request.CheckEmailRequest
import com.yapp.core.data.remote.model.request.FcmTokenRequest
import com.yapp.core.data.remote.model.request.LoginRequest
import com.yapp.core.data.remote.model.request.toData
import com.yapp.core.data.remote.model.response.toModel
import com.yapp.dataapi.AuthRepository
import com.yapp.model.SignUpInfo
import com.yapp.model.SignUpResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull

internal class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authApi: AuthApi,
    private val alarmApi: AlarmApi,
    private val securityPreferences: SecurityPreferences,
    private val dataStore: DataStore<PositionConfigs>,
) : AuthRepository {

    override suspend fun signUp(request: SignUpInfo): SignUpResult {
        val positionConfigs = dataStore.data.firstOrNull() ?: PositionConfigs.getDefaultInstance()
        val fcmToken = FirebaseMessaging.getInstance().token.await()

        val deviceAlarmToggle = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED
        } else {
            true // Android 12 이하에서는 자동 허용
        }

        val response = authApi.signUp(
            request.toData(
                positionConfigs = positionConfigs,
                fcmToken = fcmToken,
                deviceAlarmToggle = deviceAlarmToggle,
            )
        )

        response.getOrNull()?.let {
            securityPreferences.setAccessToken(it.accessToken)
            securityPreferences.setRefreshToken(it.refreshToken)
        }

        return response.toModel()
    }

    override suspend fun login(email: String, password: String) {
        val response = authApi.login(LoginRequest(email = email, password = password))
        securityPreferences.setAccessToken(response.accessToken)
        securityPreferences.setRefreshToken(response.refreshToken)
        val fcmToken = FirebaseMessaging.getInstance().token.await()
        alarmApi.putFcmToken(
            FcmTokenRequest(
                fcmToken = fcmToken
            )
        )
    }

    override suspend fun clearTokens() {
        securityPreferences.clearAll()
    }

    override suspend fun checkEmail(email: String) {
        authApi.checkEmail(CheckEmailRequest(email = email))
    }
}