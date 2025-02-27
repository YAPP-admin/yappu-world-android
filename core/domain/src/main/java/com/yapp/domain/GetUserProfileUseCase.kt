package com.yapp.domain

import com.yapp.dataapi.UserProfileRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        userProfileRepository.getUserProfile()
    }
}


