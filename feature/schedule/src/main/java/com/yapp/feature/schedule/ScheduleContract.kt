package com.yapp.feature.schedule

import com.yapp.model.ScheduleList
import java.time.LocalDate

data class ScheduleState(
    val isLoading: Boolean = true,
    val selectedTab: ScheduleTab = ScheduleTab.ALL,
    val selectedYear: Int = LocalDate.now().year,
    val selectedMonth: Int = LocalDate.now().monthValue,
    val schedules: ScheduleList = ScheduleList(dates = emptyList())
)

sealed interface ScheduleIntent {
    data object EnterScheduleScreen: ScheduleIntent
    data class SelectTab(val tab: ScheduleTab): ScheduleIntent
    data object ClickPreviousMonth: ScheduleIntent
    data object ClickNextMonth: ScheduleIntent
}

sealed interface ScheduleSideEffect {
    data class ShowToast(val message: String): ScheduleSideEffect
}

enum class ScheduleTab(val index: Int, val labelResId: Int) {
    ALL(0, R.string.schedule_tab_all),
    SESSION(1, R.string.schedule_tab_session);

    companion object {
        fun fromIndex(index: Int): ScheduleTab {
            return entries.find { it.index == index } ?: ALL
        }
    }
}