package com.yapp.core.data.data.repository

import com.yapp.core.data.remote.api.UserProfileApi
import com.yapp.dataapi.UserProfileRepository
import com.yapp.model.UserInfo
import javax.inject.Inject

internal class UserProfileRepositoryImpl @Inject constructor(
    private val api: UserProfileApi,
) : UserProfileRepository {
    override suspend fun getUserProfile(): UserInfo = api.getUserProfile().toUserInfo()
}