package com.yapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.AttendanceRepository
import com.yapp.domain.SessionsUseCase
import com.yapp.domain.runCatchingIgnoreCancelled
import com.yapp.model.AttendanceInfo
import com.yapp.model.AttendanceStatus
import com.yapp.model.HomeSessionList
import com.yapp.model.exceptions.InvalidTokenException
import com.yapp.model.exceptions.UserNotFoundForEmailException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val sessionsUseCase: SessionsUseCase,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

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
            HomeIntent.EnterHomeScreen -> { loadHomeInfo( reduce,postSideEffect)  }
            HomeIntent.ClickShowAllSession -> postSideEffect(HomeSideEffect.NavigateToSchedule)
            HomeIntent.ClickRequestAttendCode -> {
                reduce {
                    copy(showAttendCodeBottomSheet = true)
                }
            }
            HomeIntent.ClickDismissDialog -> {
                reduce {
                    copy(showAttendCodeBottomSheet = false)
                }
            }
            is HomeIntent.ClickRequestAttendance -> {
                requestAttendance(intent.sessionId, intent.code, state, reduce)
            }
        }
    }

    private fun loadHomeInfo(
        reduce: (HomeState.() -> HomeState) -> Unit,
        postSideEffect: (HomeSideEffect) -> Unit
    ) {
        viewModelScope.launch {
            runCatchingIgnoreCancelled {
                sessionsUseCase.invoke()
            }.onSuccess { homeSessions ->
                reduce {
                    copy(
                        sessionList = homeSessions.sessions,
                        upcomingSession = homeSessions.upcomingSessionInfo,
                    )
                }
            }.onFailure { e ->
                when (e) {
                    is UserNotFoundForEmailException, is InvalidTokenException -> postSideEffect(HomeSideEffect.NavigateToLogin)
                    else -> e.record()
                }
            }
        }
    }

    private fun requestAttendance(
        sessionId: String,
        code: String,
        state: HomeState,
        reduce: (HomeState.() -> HomeState) -> Unit
    ) {
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
                        showAttendCodeBottomSheet = false
                    )
                }
            }.onFailure { it.record() }
        }
    }
}
