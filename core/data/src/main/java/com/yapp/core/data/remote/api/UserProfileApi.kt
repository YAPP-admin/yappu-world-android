package com.yapp.core.data.remote.api

import com.yapp.core.data.remote.model.response.UserProfileResponse
import retrofit2.http.GET

interface UserProfileApi {
    @GET("v1/users/profile")
    suspend fun getUserProfile() : UserProfileResponse
}