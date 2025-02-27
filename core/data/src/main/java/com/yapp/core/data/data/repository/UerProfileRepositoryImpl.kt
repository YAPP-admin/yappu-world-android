package com.yapp.core.data.data.repository

import com.yapp.core.data.remote.api.UserProfileApi
import com.yapp.dataapi.UserProfileRepository
import com.yapp.model.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class UerProfileRepositoryImpl @Inject constructor(
    private val api: UserProfileApi,
) : UserProfileRepository {
    override suspend fun getUserProfile(): Flow<UserInfo> = flow {
        emit( api.getUserProfile().toUserInfo())
    }
}