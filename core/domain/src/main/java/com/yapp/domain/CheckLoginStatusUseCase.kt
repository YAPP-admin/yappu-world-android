package com.yapp.domain

import com.yapp.dataapi.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class CheckLoginStatusUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() : Boolean  {
        val token = userRepository.getUserAccessToken().firstOrNull()
        return !token.isNullOrBlank()
    }
}