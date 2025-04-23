package com.yapp.domain

import com.yapp.dataapi.OperationsRepository
import javax.inject.Inject

class GetPositionConfigsUseCase @Inject constructor(
    private val operationsRepository: OperationsRepository,
) {
    operator fun invoke() = operationsRepository.getPositionConfigs()
}
