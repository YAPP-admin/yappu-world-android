package com.yapp.domain

import com.yapp.data_api.UnAuthorizedUserRepository
import com.yapp.model.SignUpInfo
import kotlinx.coroutines.delay
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val unAuthorizedUserRepository: UnAuthorizedUserRepository,
) {
    suspend operator fun invoke(param: SignUpInfo) = runCatchingIgnoreCancelled {
        unAuthorizedUserRepository.signUp(param)
    }
}
