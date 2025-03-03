package com.yapp.core.data.remote.api

import com.yapp.core.data.remote.model.response.UserProfileResponse
import retrofit2.http.DELETE
import retrofit2.http.GET

interface AuthorizedUserApi {
    @GET("v1/users/profile")
    suspend fun getUserProfile() : UserProfileResponse

    @DELETE("v1/auth/user")
    suspend fun deleteUser()
}