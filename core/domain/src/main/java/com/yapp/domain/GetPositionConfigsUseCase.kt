package com.yapp.domain

import com.yapp.dataapi.ConfigRepository
import javax.inject.Inject

class GetPositionConfigsUseCase @Inject constructor(
    private val configRepository: ConfigRepository,
) {
    operator fun invoke() = configRepository.getPositionConfigs()
}
