package com.yapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.AttendanceRepository
import com.yapp.dataapi.ScheduleRepository
import com.yapp.domain.runCatchingIgnoreCancelled
import com.yapp.model.AttendanceInfo
import com.yapp.model.AttendanceStatus
import com.yapp.model.HomeSessionList
import com.yapp.model.exceptions.CodeNotCorrectException
import com.yapp.model.exceptions.InvalidTokenException
import com.yapp.model.exceptions.UserNotFoundForEmailException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {
    private var isInitialized = false

    val store: MviIntentStore<HomeState, HomeIntent, HomeSideEffect> =
        mviIntentStore(
            initialState = HomeState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: HomeIntent,
        state: HomeState,
        reduce: (HomeState.() -> HomeState) -> Unit,
        postSideEffect: (HomeSideEffect) -> Unit,
    ) {
        when (intent) {
            HomeIntent.EnterHomeScreen -> {
                if (isInitialized) return

                viewModelScope.launch {
                    joinAll(
                        loadSessionInfo(reduce, postSideEffect),
                        loadUpcomingSessionInfo(reduce, postSideEffect)
                    )
                    isInitialized = true
                }
            }

            HomeIntent.RefreshUpcomingSession -> {
                loadUpcomingSessionInfo(reduce, postSideEffect)
            }

            HomeIntent.ClickShowAllSession -> postSideEffect(HomeSideEffect.NavigateToSchedule)
            HomeIntent.ClickRequestAttendCode -> {
                reduce {
                    copy(showAttendCodeBottomSheet = true)
                }
            }

            HomeIntent.ClickDismissDialog -> {
                reduce {
                    copy(
                        showAttendCodeBottomSheet = false,
                        showAttendanceCodeError = false,
                        attendanceCodeDigits = List(4) { "" },
                    )
                }
            }

            is HomeIntent.ChangeAttendanceCodeDigits -> {
                reduce {
                    copy(
                        attendanceCodeDigits = intent.code,
                        showAttendanceCodeError = false
                    )
                }
            }

            is HomeIntent.ClickRequestAttendance -> {
                requestAttendance(
                    state.upcomingSession?.sessionId,
                    state.attendanceCode,
                    state,
                    reduce,
                    postSideEffect,
                )
            }
        }
    }

    private fun loadSessionInfo(
        reduce: (HomeState.() -> HomeState) -> Unit,
        postSideEffect: (HomeSideEffect) -> Unit
    ) = viewModelScope.launch {
        reduce { copy(isLoading = true) }
        runCatchingIgnoreCancelled {
            scheduleRepository.getSessions()
        }.onSuccess { homeSessions ->
            reduce {
                copy(
                    sessionList = homeSessions
                )
            }
        }.onFailure { e ->
            when (e) {
                is InvalidTokenException -> postSideEffect(HomeSideEffect.NavigateToLogin)
                else -> e.record()
            }
        }
        reduce { copy(isLoading = false) }
    }


    private fun loadUpcomingSessionInfo(
        reduce: (HomeState.() -> HomeState) -> Unit,
        postSideEffect: (HomeSideEffect) -> Unit
    ) = viewModelScope.launch {
        reduce { copy(isLoading = true) }
        runCatchingIgnoreCancelled {
            scheduleRepository.refreshUpcomingSessions()
        }.onSuccess { upcomingSessionInfo ->
            reduce {
                copy(
                    upcomingSession = upcomingSessionInfo
                )
            }
        }.onFailure { e ->
            when (e) {
                is InvalidTokenException -> postSideEffect(HomeSideEffect.NavigateToLogin)
                else -> e.record()
            }
        }
        reduce { copy(isLoading = false) }
    }


    private fun requestAttendance(
        sessionId: String?,
        code: String,
        state: HomeState,
        reduce: (HomeState.() -> HomeState) -> Unit,
        postSideEffect: (HomeSideEffect) -> Unit
    ) {
        if (sessionId == null) return

        viewModelScope.launch {
            runCatchingIgnoreCancelled {
                attendanceRepository.postAttendance(AttendanceInfo(sessionId, code))
            }.onSuccess {
                val updatedSessions = HomeSessionList(
                    sessions = state.sessionList.sessions.map { session ->
                        if (session.id == sessionId) {
                            session.copy(attendanceStatus = AttendanceStatus.ATTENDED)
                        } else {
                            session
                        }
                    },
                    upcomingSessionId = state.sessionList.upcomingSessionId
                )

                reduce {
                    copy(
                        sessionList = updatedSessions,
                        upcomingSession = state.upcomingSession?.copy(
                            canCheckIn = false,
                            status = AttendanceStatus.ATTENDED
                        ),
                        showAttendCodeBottomSheet = false,
                        showAttendanceCodeError = false
                    )
                }
            }.onFailure {
                when (it) {
                    is InvalidTokenException -> postSideEffect(HomeSideEffect.NavigateToLogin)
                    is CodeNotCorrectException -> {
                        reduce { copy(showAttendanceCodeError = true) }
                    }
                    else -> {
                        postSideEffect(HomeSideEffect.HandleException(it))
                        it.record()
                    }
                }
            }
        }
    }
}
