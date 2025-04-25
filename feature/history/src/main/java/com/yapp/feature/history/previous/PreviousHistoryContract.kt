package com.yapp.feature.history.previous

data class PreviousHistoryState(
    val items: List<History> = emptyList()
) {
    data class History(
        val generation: Int,
        val position: String,
        val activityStartDate: String?,
        val activityEndDate: String?
    )
}

sealed interface PreviousHistorySideEffect {
    data object Finish : PreviousHistorySideEffect
}

sealed interface PreviousHistoryIntent {
    data object OnEntryScreen : PreviousHistoryIntent
    data object OnClickBackButton : PreviousHistoryIntent
}

