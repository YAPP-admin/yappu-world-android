package com.yapp.core.data.remote.retrofit

import com.yapp.core.data.local.SecurityPreferences
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val securityPreferences: SecurityPreferences,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            securityPreferences.flowAccessToken().firstOrNull() ?: ""
        }

        val request = chain.request().newBuilder().apply {
            addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(request.build())
    }
}
