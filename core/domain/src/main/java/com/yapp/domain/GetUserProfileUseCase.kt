package com.yapp.domain

import com.yapp.dataapi.AuthorizedUserRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val authorizedUserRepository: AuthorizedUserRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        authorizedUserRepository.getUserProfile()
    }
}


