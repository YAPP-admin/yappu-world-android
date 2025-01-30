package com.yapp.domain

import com.yapp.dataapi.UnAuthorizedUserRepository
import com.yapp.model.SignUpInfo
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val unAuthorizedUserRepository: UnAuthorizedUserRepository,
) {
    suspend operator fun invoke(param: SignUpInfo) = runCatchingIgnoreCancelled {
        unAuthorizedUserRepository.signUp(param)
    }
}
