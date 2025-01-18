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

interface MviStore<STATE, EFFECT> {
    val uiState: StateFlow<STATE>
    val sideEffects: Flow<EFFECT>
    fun setState(reduce: STATE.() -> STATE)
    fun postSideEffect(effect: EFFECT)
}

class MviStoreImpl<STATE, EFFECT>(
    initialUiState: STATE,
    private val coroutineScope: CoroutineScope,
) : MviStore<STATE, EFFECT> {
    private val _uiState = MutableStateFlow(initialUiState)
    override val uiState: StateFlow<STATE> = _uiState.asStateFlow()
    private val _sideEffects = Channel<EFFECT>()
    override val sideEffects: Flow<EFFECT> = _sideEffects.receiveAsFlow()
    override fun setState(reduce: STATE.() -> STATE) {
        _uiState.update(reduce)
    }

    override fun postSideEffect(effect: EFFECT) {
        coroutineScope.launch {
            _sideEffects.send(effect)
        }
    }
}

fun <STATE, EFFECT> ViewModel.mviStore(
    initialState: STATE
): MviStore<STATE, EFFECT> = MviStoreImpl(
    initialUiState = initialState,
    coroutineScope = viewModelScope,
)