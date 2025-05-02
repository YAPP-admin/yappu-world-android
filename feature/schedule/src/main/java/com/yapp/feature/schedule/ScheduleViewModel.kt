package com.yapp.feature.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.ScheduleRepository
import com.yapp.domain.runCatchingIgnoreCancelled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
) : ViewModel() {

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
                loadScheduleInfo(state, state.selectedYear, state.selectedMonth, reduce)
            }

            is ScheduleIntent.SelectTab -> {
                if (state.selectedTab == intent.tab) return
                reduce { copy(selectedTab = intent.tab) }
                when (intent.tab) {
                    ScheduleTab.ALL -> loadScheduleInfo(state, state.selectedYear, state.selectedMonth, reduce)
                    ScheduleTab.SESSION -> {
                        loadUpcomingSessionInfo(state, reduce)
                        loadSessions(state, reduce)
                    }
                }
            }

            is ScheduleIntent.RefreshTab -> {
                when (intent.tab) {
                    ScheduleTab.ALL -> refreshScheduleInfo(state, state.selectedYear, state.selectedMonth, reduce)
                    ScheduleTab.SESSION -> {
                        refreshUpcomingSessionInfo(reduce)
                        refreshSessions(reduce)
                    }
                }
            }

            ScheduleIntent.ClickPreviousMonth -> {
                val (newYear, newMonth) = calculatePreviousMonth(state.selectedYear, state.selectedMonth)
                reduce { copy(selectedYear = newYear, selectedMonth = newMonth) }
                loadScheduleInfo(state, newYear, newMonth, reduce)
            }

            ScheduleIntent.ClickNextMonth -> {
                val (newYear, newMonth) = calculateNextMonth(state.selectedYear, state.selectedMonth)
                reduce { copy(selectedYear = newYear, selectedMonth = newMonth) }
                loadScheduleInfo(state, newYear, newMonth, reduce)
            }
        }
    }

    private fun loadScheduleInfo(
        state: ScheduleState,
        year: Int,
        month: Int,
        reduce: (ScheduleState.() -> ScheduleState) -> Unit
    ) = viewModelScope.launch {
        val cacheKey = year to month
        val cached = state.schedules[cacheKey]
        if (cached != null) return@launch

        runCatchingIgnoreCancelled {
            reduce { copy(isLoading = true) }
            val result = scheduleRepository.getSchedules(year, month)
            reduce {
                copy(
                    isLoading = false,
                    schedules = schedules.toMutableMap().apply { put(cacheKey, result) }
                )
            }
        }.onFailure { e ->
            e.record()
        }
    }

    private fun loadUpcomingSessionInfo(
        state: ScheduleState,
        reduce: (ScheduleState.() -> ScheduleState) -> Unit
    ) = viewModelScope.launch {
        if (state.upcomingSessionInfo != null) return@launch

        runCatchingIgnoreCancelled {
            reduce { copy(isLoading = true) }
            val info = scheduleRepository.getUpcomingSessions()
            reduce { copy(isLoading = false, upcomingSessionInfo = info) }
        }.onFailure { e ->
            e.record()
        }
    }

    private fun loadSessions(
        state: ScheduleState,
        reduce: (ScheduleState.() -> ScheduleState) -> Unit
    ) = viewModelScope.launch {
        if (state.sessions.dates.isNotEmpty()) return@launch

        runCatchingIgnoreCancelled {
            reduce { copy(isLoading = true) }
            val result = scheduleRepository.getDateGroupedSessions()
            reduce { copy(isLoading = false, sessions = result) }
        }.onFailure { e ->
            e.record()
        }
    }

    private fun refreshScheduleInfo(
        state: ScheduleState,
        year: Int,
        month: Int,
        reduce: (ScheduleState.() -> ScheduleState) -> Unit
    ) = viewModelScope.launch {
        runCatchingIgnoreCancelled {
            reduce { copy(isLoading = true) }
            val schedules = state.schedules
            val refreshSchedules = scheduleRepository.getSchedules(year, month)
            reduce {
                copy(
                    isLoading = false,
                    schedules = schedules.toMutableMap().apply { put(year to month, refreshSchedules) }
                )
            }
        }.onFailure { e ->
            e.record()
        }
    }

    private fun refreshUpcomingSessionInfo(
        reduce: (ScheduleState.() -> ScheduleState) -> Unit
    ) = viewModelScope.launch {
        runCatchingIgnoreCancelled {
            reduce { copy(isLoading = true) }
            val upcomingSessionInfo = scheduleRepository.getUpcomingSessions()
            reduce {
                copy(
                    isLoading = false,
                    upcomingSessionInfo = upcomingSessionInfo
                )
            }
        }.onFailure { e ->
            e.record()
        }
    }

    private fun refreshSessions(
        reduce: (ScheduleState.() -> ScheduleState) -> Unit
    ) = viewModelScope.launch {
        runCatchingIgnoreCancelled {
            reduce { copy(isLoading = true) }
            val sessions = scheduleRepository.getDateGroupedSessions()
            reduce {
                copy(
                    isLoading = false,
                    sessions = sessions
                )
            }
        }.onFailure { e ->
            e.record()
        }
    }

    private fun calculatePreviousMonth(year: Int, month: Int): Pair<Int, Int> {
        return if (month == 1) year - 1 to 12 else year to month - 1
    }

    private fun calculateNextMonth(year: Int, month: Int): Pair<Int, Int> {
        return if (month == 12) year + 1 to 1 else year to month + 1
    }
}