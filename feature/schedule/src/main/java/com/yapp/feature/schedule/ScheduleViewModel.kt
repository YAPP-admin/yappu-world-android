package com.yapp.feature.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.ScheduleRepository
import com.yapp.model.ScheduleList
import com.yapp.model.UpcomingSessionInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
) : ViewModel() {

    private val schedulesCache = mutableMapOf<Pair<Int, Int>, ScheduleList>()
    private var sessionsCache: ScheduleList? = null
    private var upcomingSessionCache: UpcomingSessionInfo? = null

    val store: MviIntentStore<ScheduleState, ScheduleIntent, ScheduleSideEffect> =
        mviIntentStore(
            initialState = ScheduleState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: ScheduleIntent,
        state: ScheduleState,
        reduce: (ScheduleState.() -> ScheduleState) -> Unit,
        postSideEffect: (ScheduleSideEffect) -> Unit,
    ) {
        when (intent) {
            ScheduleIntent.EnterScheduleScreen -> {
                loadScheduleInfo(state.selectedYear, state.selectedMonth, reduce)
            }

            is ScheduleIntent.SelectTab -> {
                if (state.selectedTab == intent.tab) return
                reduce { copy(selectedTab = intent.tab) }
                when (intent.tab) {
                    ScheduleTab.ALL -> loadScheduleInfo(state.selectedYear, state.selectedMonth, reduce)
                    ScheduleTab.SESSION -> {
                        loadUpcomingSessionInfo(reduce)
                        loadSessions(reduce)
                    }
                }
            }

            is ScheduleIntent.RefreshTab -> {
                when (intent.tab) {
                    ScheduleTab.ALL -> refreshScheduleInfo(state.selectedYear, state.selectedMonth, reduce)
                    ScheduleTab.SESSION -> {
                        refreshUpcomingSessionInfo(reduce)
                        refreshSessions(reduce)
                    }
                }
            }

            ScheduleIntent.ClickPreviousMonth -> {
                val (newYear, newMonth) = calculatePreviousMonth(state.selectedYear, state.selectedMonth)
                reduce { copy(selectedYear = newYear, selectedMonth = newMonth) }
                loadScheduleInfo(newYear, newMonth, reduce)
            }
            ScheduleIntent.ClickNextMonth -> {
                val (newYear, newMonth) = calculateNextMonth(state.selectedYear, state.selectedMonth)
                reduce { copy(selectedYear = newYear, selectedMonth = newMonth) }
                loadScheduleInfo(newYear, newMonth, reduce)
            }
        }
    }

    private fun loadScheduleInfo(
        year: Int,
        month: Int,
        reduce: (ScheduleState.() -> ScheduleState) -> Unit
    ) = viewModelScope.launch {
        val cacheKey = year to month
        val cachedSchedules = schedulesCache[cacheKey]
        if (cachedSchedules != null) {
            reduce { copy(schedules = cachedSchedules) }
            return@launch
        }

        reduce { copy(isLoading = true) }
        val schedules = scheduleRepository.getSchedules(year, month)
        schedulesCache[cacheKey] = schedules
        reduce { copy(isLoading = false, schedules = schedules) }
    }

    private fun loadUpcomingSessionInfo(
        reduce: (ScheduleState.() -> ScheduleState) -> Unit
    ) = viewModelScope.launch {
        upcomingSessionCache?.let {
            reduce { copy(upcomingSessionInfo = it) }
            return@launch
        }

        reduce { copy(isLoading = true) }
        val upcomingSessionInfo = scheduleRepository.getUpcomingSessions()
        upcomingSessionCache = upcomingSessionInfo
        reduce { copy(isLoading = false, upcomingSessionInfo = upcomingSessionInfo) }
    }

    private fun loadSessions(
        reduce: (ScheduleState.() -> ScheduleState) -> Unit
    ) = viewModelScope.launch {
        sessionsCache?.let {
            reduce { copy(sessions = it) }
            return@launch
        }

        reduce { copy(isLoading = true) }
        val sessions = scheduleRepository.getDateGroupedSessions()
        sessionsCache = sessions
        reduce { copy(isLoading = false, sessions = sessions) }
    }

    private fun refreshScheduleInfo(
        year: Int,
        month: Int,
        reduce: (ScheduleState.() -> ScheduleState) -> Unit
    ) = viewModelScope.launch {
        reduce { copy(isLoading = true) }
        val schedules = scheduleRepository.getSchedules(year, month)
        schedulesCache[year to month] = schedules
        reduce { copy(isLoading = false, schedules = schedules) }
    }

    private fun refreshUpcomingSessionInfo(
        reduce: (ScheduleState.() -> ScheduleState) -> Unit
    ) = viewModelScope.launch {
        reduce { copy(isLoading = true) }
        val upcomingSessionInfo = scheduleRepository.getUpcomingSessions()
        upcomingSessionCache = upcomingSessionInfo
        reduce { copy(isLoading = false, upcomingSessionInfo = upcomingSessionInfo) }
    }

    private fun refreshSessions(
        reduce: (ScheduleState.() -> ScheduleState) -> Unit
    ) = viewModelScope.launch {
        reduce { copy(isLoading = true) }
        val sessions = scheduleRepository.getDateGroupedSessions()
        sessionsCache = sessions
        reduce { copy(isLoading = false, sessions = sessions) }
    }

    private fun calculatePreviousMonth(year: Int, month: Int): Pair<Int, Int> {
        return if (month == 1) year - 1 to 12 else year to month - 1
    }

    private fun calculateNextMonth(year: Int, month: Int): Pair<Int, Int> {
        return if (month == 12) year + 1 to 1 else year to month + 1
    }
}