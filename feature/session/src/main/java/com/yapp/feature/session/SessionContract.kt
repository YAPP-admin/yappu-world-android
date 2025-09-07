package com.yapp.feature.session

data class SessionState(
    val isLoading: Boolean = false,
)

sealed interface SessionIntent {
    data object EnterSessionScreen : SessionIntent
    data object Refresh : SessionIntent
}

sealed interface SessionSideEffect {
    data class ShowToast(val message: String) : SessionSideEffect
    data class HandleException(val throwable: Throwable) : SessionSideEffect
}

