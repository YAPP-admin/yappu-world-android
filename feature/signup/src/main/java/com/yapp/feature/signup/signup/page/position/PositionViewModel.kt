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
                reduce { copy(name = intent.name) }
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

            is PositionIntent.GenerationChange -> {
                val newCurrentActivityUnit = state.currentActivityUnit.copy(generation = intent.generation.toIntOrNull())
                reduce { copy(currentActivityUnit = newCurrentActivityUnit) }
                postSideEffect(PositionSideEffect.ActivityUnitsChanged(newCurrentActivityUnit + state.previousActivityUnit))
            }

            is PositionIntent.PositionChange -> {
                val newCurrentActivityUnit = state.currentActivityUnit.copy(position = intent.position)
                reduce { copy(currentActivityUnit = newCurrentActivityUnit) }
                postSideEffect(PositionSideEffect.ActivityUnitsChanged(newCurrentActivityUnit + state.previousActivityUnit))
                postSideEffect(PositionSideEffect.ClearFocus)
            }

            is PositionIntent.PreviousGenerationChange -> {
                val updatedList = state.previousActivityUnit.replace(intent.index) {
                    it.copy(generation = intent.generation.toIntOrNull())
                }
                reduce { copy(previousActivityUnit = updatedList) }
                postSideEffect(PositionSideEffect.ActivityUnitsChanged(state.currentActivityUnit + updatedList))
            }


            is PositionIntent.PreviousPositionChange -> {
                val updatedList = state.previousActivityUnit.replace(intent.index) {
                    it.copy(position = intent.position)
                }
                reduce { copy(previousActivityUnit = updatedList) }
                postSideEffect(PositionSideEffect.ActivityUnitsChanged(state.currentActivityUnit + updatedList))
                postSideEffect(PositionSideEffect.ClearFocus)
            }
        }
    }
}
