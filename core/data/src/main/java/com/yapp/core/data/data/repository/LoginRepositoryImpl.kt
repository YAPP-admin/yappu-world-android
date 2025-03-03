package com.yapp.core.data.data.repository

import android.content.Context
import com.google.firebase.messaging.FirebaseMessaging
import com.yapp.core.data.local.SecurityPreferences
import com.yapp.core.data.remote.api.UnAuthorizedUserApi
import com.yapp.core.data.remote.model.request.LoginRequest
import com.yapp.dataapi.LoginRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

internal class LoginRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val unauthorizedUserApi: UnAuthorizedUserApi,
    private val securityPreferences: SecurityPreferences,
    private val alarmRepositoryImpl: AlarmRepositoryImpl,
) : LoginRepository {
    override suspend fun login(email: String, password: String) {
        val response = unauthorizedUserApi.login(LoginRequest(email = email, password = password))
        securityPreferences.setAccessToken(response.accessToken)
        securityPreferences.setRefreshToken(response.refreshToken)
        val fcmToken = FirebaseMessaging.getInstance().token.await()
        alarmRepositoryImpl.updateFcmToken(fcmToken = fcmToken)
    }
}