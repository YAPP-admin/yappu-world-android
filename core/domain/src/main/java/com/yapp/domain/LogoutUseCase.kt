package com.yapp.domain

import com.yapp.dataapi.AlarmRepository
import com.yapp.dataapi.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        alarmRepository.updateFcmToken("")
        authRepository.clearTokens()
    }
}
