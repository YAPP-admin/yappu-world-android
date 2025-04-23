package com.yapp.feature.signup.signup.page.position

import androidx.lifecycle.ViewModel
import com.yapp.core.ui.extension.replace
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.model.ActivityUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PositionViewModel @Inject constructor() : ViewModel() {
    val store: MviIntentStore<PositionState, PositionIntent, PositionSideEffect> =
        mviIntentStore(
            initialState = PositionState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: PositionIntent,
        state: PositionState,
        reduce: (PositionState.() -> PositionState) -> Unit,
        postSideEffect: (PositionSideEffect) -> Unit
    ) {
        when (intent) {
            is PositionIntent.EnterScreen -> {
                reduce { copy(name = intent.name, positions = intent.positions) }
            }

            is PositionIntent.ClickAddPreviousGenerationButton -> {
                val newPreviousActivityUnit = state.previousActivityUnit + ActivityUnit()
                reduce { copy(previousActivityUnit = newPreviousActivityUnit) }
                postSideEffect(PositionSideEffect.ActivityUnitsChanged(state.currentActivityUnit + newPreviousActivityUnit))
            }

            is PositionIntent.ClickDeletePreviousGenerationButton -> {
                val toRemove = state.previousActivityUnit[intent.index]
                val newPreviousActivityUnit = state.previousActivityUnit.minus(toRemove)
                reduce { copy(previousActivityUnit = newPreviousActivityUnit) }
                postSideEffect(PositionSideEffect.ActivityUnitsChanged(state.currentActivityUnit + newPreviousActivityUnit))
            }

            is PositionIntent.ChangeGeneration -> {
                val newCurrentActivityUnit = state.currentActivityUnit.copy(generation = intent.generation.toIntOrNull())
                reduce { copy(currentActivityUnit = newCurrentActivityUnit) }
                postSideEffect(PositionSideEffect.ActivityUnitsChanged(newCurrentActivityUnit + state.previousActivityUnit))
            }

            is PositionIntent.ChangePosition -> {
                val newCurrentActivityUnit = state.currentActivityUnit.copy(position = intent.position)
                reduce { copy(currentActivityUnit = newCurrentActivityUnit) }
                postSideEffect(PositionSideEffect.ActivityUnitsChanged(newCurrentActivityUnit + state.previousActivityUnit))
            }

            is PositionIntent.ChangePreviousGeneration -> {
                val updatedList = state.previousActivityUnit.replace(intent.index) {
                    it.copy(generation = intent.generation.toIntOrNull())
                }
                reduce { copy(previousActivityUnit = updatedList) }
                postSideEffect(PositionSideEffect.ActivityUnitsChanged(state.currentActivityUnit + updatedList))
            }


            is PositionIntent.ChangePreviousPosition -> {
                val updatedList = state.previousActivityUnit.replace(intent.index) {
                    it.copy(position = intent.position)
                }
                reduce { copy(previousActivityUnit = updatedList) }
                postSideEffect(PositionSideEffect.ActivityUnitsChanged(state.currentActivityUnit + updatedList))
            }

            PositionIntent.DropdownMenuShown -> postSideEffect(PositionSideEffect.ClearFocus)
        }
    }
}
