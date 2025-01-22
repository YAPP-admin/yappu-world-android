package com.yapp.core.data.data.repository

import com.yapp.core.data.remote.api.UnAuthorizedUserApi
import com.yapp.core.data.remote.model.request.toData
import com.yapp.data_api.UnAuthorizedUserRepository
import com.yapp.model.SignUpInfo
import com.yapp.model.YappJWT
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull

internal class UnAuthorizedUserRepositoryImpl @Inject constructor(
    private val api: UnAuthorizedUserApi,
): UnAuthorizedUserRepository {
    override suspend fun signUp(request: SignUpInfo): YappJWT? {
        return api.signUp(
            request.toData()
        ).getOrNull()?.toModel()
    }
}