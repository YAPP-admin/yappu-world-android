package com.yapp.domain

import com.yapp.dataapi.AuthRepository
import com.yapp.model.SignUpInfo
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(param: SignUpInfo) = runCatchingIgnoreCancelled {
        authRepository.signUp(param)
    }
}
