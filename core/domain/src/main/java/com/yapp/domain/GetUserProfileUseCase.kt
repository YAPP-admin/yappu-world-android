package com.yapp.domain

import com.yapp.dataapi.UserRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        userRepository.getUserProfile()
    }
}


