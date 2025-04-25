package com.yapp.feature.history.previous

import com.yapp.dataapi.UserRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class PreviousHistoryUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    operator fun invoke() = userRepository.getUserActivityHistories().map { result ->
        PreviousHistoryState(
            items = result.activityUnits.map { unit ->
                PreviousHistoryState.History(
                    generation = unit.generation,
                    position = unit.position,
                    activityStartDate = unit.activityStartDate,
                    activityEndDate = unit.activityEndDate
                )
            }
        )
    }
}
