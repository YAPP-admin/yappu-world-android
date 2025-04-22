package com.yapp.feature.schedule

import com.yapp.model.ScheduleList

data class ScheduleState(
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val selectedYear: Int = 2025,
    val selectedMonth: Int = 4,
    val schedules: ScheduleList = ScheduleList(dates = emptyList())
)

sealed interface ScheduleIntent {
    data object EnterScheduleScreen: ScheduleIntent
    data class SelectTab(val tabIndex: Int): ScheduleIntent
    data object ClickPreviousMonth: ScheduleIntent
    data object ClickNextMonth: ScheduleIntent
}

sealed interface ScheduleSideEffect {
    data class ShowToast(val message: String): ScheduleSideEffect
}
