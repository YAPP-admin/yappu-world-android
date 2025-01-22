package com.yapp.domain

import com.yapp.data_api.UnAuthorizedUserRepository
import com.yapp.model.SignUpInfo
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpRepository: UnAuthorizedUserRepository,
) {
    suspend operator fun invoke(param: SignUpInfo) = kotlin.runCatching {
        signUpRepository.signUp(param)
    }
}
