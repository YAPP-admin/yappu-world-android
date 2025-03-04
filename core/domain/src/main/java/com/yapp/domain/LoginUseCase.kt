package com.yapp.domain

import com.yapp.dataapi.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String) = runCatchingIgnoreCancelled {
        authRepository.login(email = email, password = password)
    }
}