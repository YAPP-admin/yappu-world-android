package com.yapp.domain

import com.yapp.dataapi.AlarmRepository
import com.yapp.dataapi.AuthorizedUserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authorizedUserRepository: AuthorizedUserRepository,
    private val alarmRepository: AlarmRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        authorizedUserRepository.clearTokens()
        alarmRepository.updateFcmToken("")
    }
}
