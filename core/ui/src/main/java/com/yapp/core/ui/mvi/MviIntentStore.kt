package com.yapp.core.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface MviIntentStore<STATE, INTENT, EFFECT> {
    val uiState: StateFlow<STATE>
    val sideEffects: Flow<EFFECT>
    fun onIntent(intent: INTENT)
}

class MviIntentStoreImpl<STATE, INTENT, EFFECT>(
    initialState: STATE,
    private val coroutineScope: CoroutineScope,
    private val onIntent: (
        intent: INTENT,
        state: STATE,
        reduce: (STATE.() -> STATE) -> Unit,
        postSideEffect: (EFFECT) -> Unit
    ) -> Unit
) : MviIntentStore<STATE, INTENT, EFFECT> {
    private val _uiState = MutableStateFlow(initialState)
    override val uiState: StateFlow<STATE> = _uiState.asStateFlow()
    private val _sideEffects = Channel<EFFECT>(Channel.BUFFERED)
    override val sideEffects: Flow<EFFECT> = _sideEffects.receiveAsFlow()
    private fun setState(reduce: STATE.() -> STATE) {
        _uiState.update(reduce)
    }

    private fun postSideEffect(effect: EFFECT) {
        coroutineScope.launch { _sideEffects.send(effect) }
    }

    override fun onIntent(intent: INTENT) {
        onIntent(
            intent,
            _uiState.value,
            { reduce -> setState { reduce() } },
            { effect -> postSideEffect(effect) }
        )
    }
}

fun <STATE, INTENT, EFFECT> ViewModel.mviIntentStore(
    initialState: STATE,
    onIntent: (INTENT, STATE, (STATE.() -> STATE) -> Unit, (EFFECT) -> Unit) -> Unit
): MviIntentStore<STATE, INTENT, EFFECT> = MviIntentStoreImpl(
    initialState = initialState,
    coroutineScope = viewModelScope,
    onIntent = onIntent
)