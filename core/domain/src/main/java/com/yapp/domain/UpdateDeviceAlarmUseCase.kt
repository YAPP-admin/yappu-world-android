package com.yapp.domain

import com.yapp.dataapi.AlarmRepository
import javax.inject.Inject

class UpdateDeviceAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        alarmRepository.updateDeviceAlarmStatus()
    }
}


