package com.yapp.feature.history.previous

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class PreviousHistoryViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val store: MviIntentStore<PreviousHistoryState, PreviousHistoryIntent, PreviousHistorySideEffect> = mviIntentStore(
        initialState = PreviousHistoryState(),
        onIntent = ::onIntent
    )

    private fun onIntent(
        intent: PreviousHistoryIntent,
        state: PreviousHistoryState,
        reduce: (PreviousHistoryState.() -> PreviousHistoryState) -> Unit,
        sideEffect: (PreviousHistorySideEffect) -> Unit
    ) {
        when (intent) {
            PreviousHistoryIntent.OnClickBackButton -> {
                sideEffect(PreviousHistorySideEffect.Finish)
            }
            PreviousHistoryIntent.OnEntryScreen -> {
                userRepository.getUserActivityHistories().map { result ->
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
                }.catch {
                    it.record()
                }.onEach { result ->
                    reduce {
                        copy(items = result.items)
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}
