package com.yapp.official

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.official.mvi.MviStore
import com.yapp.official.mvi.mviStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class Example2ViewModel : ViewModel() {
    private val loadMoreMutex = Mutex()

    val store: MviStore<ExampleState, ExampleSideEffect> =
        mviStore(
            initialState = ExampleState(),
        )

    fun onClickDecrementButton() {
        store.postSideEffect(ExampleSideEffect.ShowMessage("Decrement"))
    }

    fun onClickIncrementButton() {
        store.postSideEffect(ExampleSideEffect.ShowMessage("Increment"))
    }

    fun onClickLoadMoreButton(
        startIndex: Int
    ) = viewModelScope.launch {
        if(loadMoreMutex.isLocked) return@launch

        loadMoreMutex.withLock {
            store.setState { copy(isLoading = true) }
            delay(3000)
            val newItems = List(10) { "Item ${startIndex + it}" }
            store.setState { copy(items = items + newItems, isLoading = false) }
            store.postSideEffect(ExampleSideEffect.ShowMessage("Load Success"))
        }
    }
}