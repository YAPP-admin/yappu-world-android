package com.yapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.AttendanceRepository
import com.yapp.dataapi.OperationsRepository
import com.yapp.dataapi.ScheduleRepository
import com.yapp.domain.runCatchingIgnoreCancelled
import com.yapp.model.AttendanceInfo
import com.yapp.model.AttendanceStatus
import com.yapp.model.exceptions.CodeNotCorrectException
import com.yapp.model.exceptions.InvalidTokenException
import com.yapp.model.exceptions.NoScheduledSessionException
import com.yapp.model.exceptions.NotFoundException
import com.yapp.model.exceptions.UndefineNoticeWriterInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val attendanceRepository: AttendanceRepository,
    private val operationsRepository: OperationsRepository,
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
                    loadUpcomingSessionInfo(reduce, postSideEffect).join()
                    isInitialized = true
                }
            }

            HomeIntent.ClickShowAllAttendanceHistory -> postSideEffect(HomeSideEffect.NavigateToAttendanceHistory)
            is HomeIntent.ClickNotice -> postSideEffect(HomeSideEffect.NavigateToNotice(intent.id))
            is HomeIntent.ClickDetail -> postSideEffect(HomeSideEffect.NavigateToSessionDetail(intent.id))

            HomeIntent.Refresh -> {
                loadUpcomingSessionInfo(reduce, postSideEffect)
            }

            is HomeIntent.ClickSessionItem -> postSideEffect(HomeSideEffect.NavigateToSessionDetail(intent.sessionId))
            HomeIntent.ClickShowAllSession -> postSideEffect(HomeSideEffect.NavigateToSchedule)
            HomeIntent.ClickBasicRuleLink -> {
                viewModelScope.launch {
                    runCatchingIgnoreCancelled { operationsRepository.getBasicRuleLink() }
                        .onSuccess { postSideEffect(HomeSideEffect.OpenUrl(it)) }
                        .onFailure { postSideEffect(HomeSideEffect.ShowToast(it.message.orEmpty())) }
                }
            }
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
                is NoScheduledSessionException -> { }
                is NotFoundException -> { }
                is UndefineNoticeWriterInfo -> { }
                else -> {
                    postSideEffect(HomeSideEffect.HandleException(e))
                    e.record()
                }
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
                reduce {
                    copy(
                        upcomingSession = state.upcomingSession?.copy(
                            canCheckIn = false,
                            status = AttendanceStatus.ATTENDED
                        ),
                        showAttendCodeBottomSheet = false,
                        showAttendanceCodeError = false
                    )
                }
            }.onFailure { e ->
                when (e) {
                    is InvalidTokenException -> postSideEffect(HomeSideEffect.NavigateToLogin)
                    is CodeNotCorrectException -> {
                        reduce { copy(showAttendanceCodeError = true) }
                    }

                    else -> {
                        postSideEffect(HomeSideEffect.HandleException(e))
                        e.record()
                    }
                }
            }
        }
    }
}
