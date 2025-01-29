package com.yapp.feature.signup.signup.page.position

import com.yapp.model.ActivityUnit

data class PositionState(
    val name: String = "",
    val currentActivityUnit: ActivityUnit = ActivityUnit(),
    val previousActivityUnit: List<ActivityUnit> = emptyList(),
)

sealed interface PositionIntent {
    data class ChangeGeneration(val generation: String) : PositionIntent
    data class ChangePosition(val position: String) : PositionIntent
    data class ChangePreviousGeneration(val index: Int, val generation: String) : PositionIntent
    data class ChangePreviousPosition(val index: Int, val position: String) : PositionIntent
    data class EnterScreen(val name: String) : PositionIntent
    data object ClickAddPreviousGenerationButton : PositionIntent
    data object DropdownMenuShown : PositionIntent

    data class ClickDeletePreviousGenerationButton(val index: Int) : PositionIntent
}

sealed interface PositionSideEffect {
    data class ActivityUnitsChanged(val activityUnits: List<ActivityUnit>) : PositionSideEffect
    data object ClearFocus : PositionSideEffect
}
