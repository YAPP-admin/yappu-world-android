package com.yapp.official

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.official.mvi.MviIntentStore
import com.yapp.official.mvi.mviIntentStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

sealed interface ExampleIntent {
    data object ClickIncrementButton : ExampleIntent
    data object ClickDecrementButton : ExampleIntent
    data class ClickLoadMoreButton(val startIndex: Int) : ExampleIntent
}

sealed interface ExampleSideEffect {
    data class ShowMessage(val message: String) : ExampleSideEffect
    data class ShowError(val error: String) : ExampleSideEffect
}

data class ExampleState(
    val items: List<String> = emptyList(),
    val isLoading: Boolean = false
)

class Example1ViewModel : ViewModel() {
    private val loadMoreMutex = Mutex()

    val store: MviIntentStore<ExampleState, ExampleIntent, ExampleSideEffect> =
        mviIntentStore(
            initialState = ExampleState(),
            onIntent = { intent, state, setState, postSideEffect ->
                when (intent) {
                    ExampleIntent.ClickDecrementButton -> postSideEffect(ExampleSideEffect.ShowMessage("Decrement"))
                    ExampleIntent.ClickIncrementButton -> postSideEffect(ExampleSideEffect.ShowMessage("Increment"))
                    is ExampleIntent.ClickLoadMoreButton -> loadMoreItem(intent, setState, postSideEffect)
                }
            }
        )

    private fun loadMoreItem(
        intent: ExampleIntent.ClickLoadMoreButton,
        setState: (ExampleState.() -> ExampleState) -> Unit,
        postSideEffect: (ExampleSideEffect) -> Unit
    ) = viewModelScope.launch {
        if (loadMoreMutex.isLocked) return@launch

        loadMoreMutex.withLock {
            setState { copy(isLoading = true) }
            delay(3000)
            val newItems = List(10) { "Item ${intent.startIndex + it}" }
            setState { copy(items = items + newItems, isLoading = false) }
            postSideEffect(ExampleSideEffect.ShowMessage("Load Success"))
        }
    }
}