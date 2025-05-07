package com.yapp.feature.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.ScheduleRepository
import com.yapp.domain.runCatchingIgnoreCancelled
import com.yapp.model.exceptions.InvalidTokenException
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
                loadScheduleInfo(state.selectedYear, state.selectedMonth, reduce, postSideEffect)
            }

            is ScheduleIntent.SelectTab -> {
                if (state.selectedTab == intent.tab) return
                reduce { copy(selectedTab = intent.tab) }
                when (intent.tab) {
                    ScheduleTab.ALL -> loadScheduleInfo(state.selectedYear, state.selectedMonth, reduce, postSideEffect)
                    ScheduleTab.SESSION -> {
                        loadUpcomingSessionInfo(reduce, postSideEffect)
                        loadSessions(reduce, postSideEffect)
                    }
                }
            }

            is ScheduleIntent.RefreshTab -> {
                when (intent.tab) {
                    ScheduleTab.ALL -> refreshScheduleInfo(state.selectedYear, state.selectedMonth, reduce, postSideEffect)
                    ScheduleTab.SESSION -> {
                        refreshUpcomingSessionInfo(reduce, postSideEffect)
                        refreshSessions(reduce, postSideEffect)
                    }
                }
            }

            ScheduleIntent.ClickPreviousMonth -> {
                val (newYear, newMonth) = calculatePreviousMonth(state.selectedYear, state.selectedMonth)
                reduce { copy(selectedYear = newYear, selectedMonth = newMonth) }
                loadScheduleInfo(newYear, newMonth, reduce, postSideEffect)
            }

            ScheduleIntent.ClickNextMonth -> {
                val (newYear, newMonth) = calculateNextMonth(state.selectedYear, state.selectedMonth)
                reduce { copy(selectedYear = newYear, selectedMonth = newMonth) }
                loadScheduleInfo(newYear, newMonth, reduce, postSideEffect)
            }
        }
    }

    private fun loadScheduleInfo(
        year: Int,
        month: Int,
        reduce: (ScheduleState.() -> ScheduleState) -> Unit,
        postSideEffect: (ScheduleSideEffect) -> Unit,
    ) = viewModelScope.launch {
        runCatchingIgnoreCancelled {
            reduce { copy(isLoading = true) }
            val result = scheduleRepository.getSchedules(year, month)
            reduce {
                copy(
                    schedules = schedules.toMutableMap().apply { put(year to month, result) }
                )
            }
        }.onFailure { e ->
            when (e) {
                is InvalidTokenException -> {
                    postSideEffect(ScheduleSideEffect.NavigateToLogin)
                }
                else -> {
                    postSideEffect(ScheduleSideEffect.HandleException(e))
                    e.record()
                }
            }
        }
        reduce { copy(isLoading = false) }
    }

    private fun loadUpcomingSessionInfo(
        reduce: (ScheduleState.() -> ScheduleState) -> Unit,
        postSideEffect: (ScheduleSideEffect) -> Unit,
    ) = viewModelScope.launch {
        reduce { copy(isLoading = true) }
        runCatchingIgnoreCancelled {
            scheduleRepository.getUpcomingSession()
        }.onSuccess {
            reduce { copy(isLoading = false, upcomingSessionInfo = it) }
        }.onFailure { e ->
            postSideEffect(ScheduleSideEffect.HandleException(e))
            e.record()
        }
        reduce { copy(isLoading = false) }
    }

    private fun loadSessions(
        reduce: (ScheduleState.() -> ScheduleState) -> Unit,
        postSideEffect: (ScheduleSideEffect) -> Unit,
    ) = viewModelScope.launch {
        reduce { copy(isLoading = true) }
        runCatchingIgnoreCancelled {
            scheduleRepository.getDateGroupedSessions()
        }.onSuccess {
            reduce { copy(isLoading = false, sessions = it) }
        }.onFailure { e ->
            postSideEffect(ScheduleSideEffect.HandleException(e))
            e.record()
        }
        reduce { copy(isLoading = false) }
    }

    private fun refreshScheduleInfo(
        year: Int,
        month: Int,
        reduce: (ScheduleState.() -> ScheduleState) -> Unit,
        postSideEffect: (ScheduleSideEffect) -> Unit,
    ) = viewModelScope.launch {
        reduce { copy(isLoading = true) }
        runCatchingIgnoreCancelled {
            scheduleRepository.refreshSchedules(year, month)
        }.onSuccess {
            reduce {
                copy(
                    schedules = schedules.toMutableMap().apply { put(year to month, it) }
                )
            }
        }.onFailure { e ->
            postSideEffect(ScheduleSideEffect.HandleException(e))
            e.record()
        }
        reduce { copy(isLoading = false) }
    }

    private fun refreshUpcomingSessionInfo(
        reduce: (ScheduleState.() -> ScheduleState) -> Unit,
        postSideEffect: (ScheduleSideEffect) -> Unit,
    ) = viewModelScope.launch {
        reduce { copy(isLoading = true) }
        runCatchingIgnoreCancelled {
            scheduleRepository.refreshUpcomingSessions()
        }.onSuccess {
            reduce {
                copy(
                    upcomingSessionInfo = it
                )
            }
        }.onFailure { e ->
            postSideEffect(ScheduleSideEffect.HandleException(e))
            e.record()
        }
        reduce { copy(isLoading = false) }
    }

    private fun refreshSessions(
        reduce: (ScheduleState.() -> ScheduleState) -> Unit,
        postSideEffect: (ScheduleSideEffect) -> Unit,
    ) = viewModelScope.launch {
        reduce { copy(isLoading = true) }
        runCatchingIgnoreCancelled {
            scheduleRepository.refreshDateGroupedSessions()
        }.onSuccess {
            reduce {
                copy(
                    sessions = it
                )
            }
        }.onFailure { e ->
            postSideEffect(ScheduleSideEffect.HandleException(e))
            e.record()
        }
        reduce { copy(isLoading = false) }
    }

    private fun calculatePreviousMonth(year: Int, month: Int): Pair<Int, Int> {
        return if (month == 1) year - 1 to 12 else year to month - 1
    }

    private fun calculateNextMonth(year: Int, month: Int): Pair<Int, Int> {
        return if (month == 12) year + 1 to 1 else year to month + 1
    }
}