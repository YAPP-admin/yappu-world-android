package com.yapp.core.data.data.repository

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.datastore.core.DataStore
import com.google.firebase.messaging.FirebaseMessaging
import com.yapp.core.data.PositionConfigs
import com.yapp.core.data.local.SecurityPreferences
import com.yapp.core.data.remote.api.UnAuthorizedUserApi
import com.yapp.core.data.remote.model.request.toData
import com.yapp.core.data.remote.model.response.toModel
import com.yapp.dataapi.UnAuthorizedUserRepository
import com.yapp.model.SignUpInfo
import com.yapp.model.SignUpResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull

internal class UnAuthorizedUserRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: UnAuthorizedUserApi,
    private val securityPreferences: SecurityPreferences,
    private val dataStore: DataStore<PositionConfigs>,
): UnAuthorizedUserRepository {

    override suspend fun signUp(request: SignUpInfo): SignUpResult {
        val positionConfigs = dataStore.data.firstOrNull() ?: PositionConfigs.getDefaultInstance()
        val fcmToken = FirebaseMessaging.getInstance().token.await()

        val deviceAlarmToggle = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED
        } else {
            true // Android 12 이하에서는 자동 허용
        }

        val response = api.signUp(
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
}