package com.yapp.feature.history.previous

data class PreviousHistoryState(
    val items: List<History> = emptyList()
) {
    data class History(
        val generation: Int,
        val position: String,
        val activityStartDate: String?,
        val activityEndDate: String?
    ) {
        val showSlot = activityEndDate.orEmpty().isNotEmpty() && activityStartDate.orEmpty().isNotEmpty()
    }
}

sealed interface PreviousHistorySideEffect {
    data object Finish : PreviousHistorySideEffect
    data object NavigateLogin : PreviousHistorySideEffect
    data class HandleException(val exception: Throwable) : PreviousHistorySideEffect
}

sealed interface PreviousHistoryIntent {
    data object OnEntryScreen : PreviousHistoryIntent
    data object OnClickBackButton : PreviousHistoryIntent
}

