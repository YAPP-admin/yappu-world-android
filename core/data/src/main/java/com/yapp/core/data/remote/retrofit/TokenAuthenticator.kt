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
        synchronized(this) {
            val accessToken = runBlocking {
                securityPreferences.flowAccessToken().firstOrNull() ?: ""
            }
            val refreshToken = runBlocking {
                securityPreferences.flowRefreshToken().firstOrNull() ?: ""
            }

            val request = ReissueTokenRequest(
                accessToken = accessToken,
                refreshToken = refreshToken
            )

            val newTokenResponse = runBlocking {
                authApi.reissueToken(request)
            }

            if (newTokenResponse.isSuccessful){
                val newAccessToken = newTokenResponse.body()?.accessToken ?: return null
                val newRefreshToken = newTokenResponse.body()?.refreshToken ?: return null
                runBlocking {
                    securityPreferences.setAccessToken(newAccessToken)
                    securityPreferences.setRefreshToken(newRefreshToken)
                }
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()
            }
            return null
        }
    }

}