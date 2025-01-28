package com.yapp.feature.signup.signup.page.position

import com.yapp.model.ActivityUnit

data class PositionState(
    val name: String = "",
    val currentActivityUnit: ActivityUnit = ActivityUnit(),
    val previousActivityUnit: List<ActivityUnit> = emptyList(),
    val position: String = "",
    val isValid: Boolean = false,
    val errorMessage: String? = null
)

sealed interface PositionIntent {
    data class GenerationChange(val generation: String) : PositionIntent
    data class PositionChange(val position: String) : PositionIntent
    data class PreviousGenerationChange(val index: Int, val generation: String) : PositionIntent
    data class PreviousPositionChange(val index: Int, val position: String) : PositionIntent
    data class EnterScreen(val name: String) : PositionIntent
    data object ClickAddPreviousGenerationButton : PositionIntent
    data class ClickDeletePreviousGenerationButton(val index: Int) : PositionIntent
}

sealed interface PositionSideEffect {
    data class ActivityUnitsChanged(val activityUnits: List<ActivityUnit>) : PositionSideEffect
}
