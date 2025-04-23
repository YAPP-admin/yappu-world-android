package com.yapp.core.data.remote.retrofit

import com.yapp.core.data.local.SecurityPreferences
import com.yapp.core.data.remote.api.AuthApi
import com.yapp.core.data.remote.model.request.ReissueTokenRequest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

internal class TokenAuthenticator @Inject constructor(
    private val securityPreferences: SecurityPreferences,
    private val authApi: AuthApi,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val accessToken = securityPreferences.flowAccessToken().firstOrNull().orEmpty()
            val refreshToken = securityPreferences.flowRefreshToken().firstOrNull().orEmpty()

            val request = ReissueTokenRequest(
                accessToken = accessToken,
                refreshToken = refreshToken
            )

            val (newAccessToken, newRefreshToken) = try {
                val response = authApi.reissueToken(request)
                securityPreferences.setAccessToken(response.accessToken)
                securityPreferences.setRefreshToken(response.refreshToken)

                response.accessToken to response.refreshToken
            } catch (e: Exception) {
                "" to ""
            }

            if (newAccessToken.isBlank() || newRefreshToken.isBlank()) {
                resetToken()
                return@runBlocking null
            }

            response.request.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
        }
    }

    private suspend fun resetToken() {
        securityPreferences.setAccessToken("")
        securityPreferences.setRefreshToken("")
    }
}