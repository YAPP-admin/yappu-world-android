package com.yapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.AttendanceRepository
import com.yapp.domain.SessionsUseCase
import com.yapp.domain.runCatchingIgnoreCancelled
import com.yapp.feature.home.convert.toState
import com.yapp.model.AttendanceInfo
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
            HomeIntent.ClickMoreButton -> postSideEffect(HomeSideEffect.NavigateToNotice)
            HomeIntent.ClickSettingButton -> postSideEffect(HomeSideEffect.NavigateToSetting)
            HomeIntent.EnterHomeScreen -> { loadHomeInfo( reduce,postSideEffect)  }
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
            is HomeIntent.ClickNoticeItem -> postSideEffect(HomeSideEffect.NavigateToNoticeDetail(intent.noticeId))
        }
    }

    private fun loadHomeInfo(
        reduce: (HomeState.() -> HomeState) -> Unit,
        postSideEffect: (HomeSideEffect) -> Unit
    ) {
        viewModelScope.launch {
            runCatchingIgnoreCancelled {
                val result = sessionsUseCase.invoke()
                val sessions = result.sessions.sessions

                reduce {
                    copy(
                        sessions = sessions.toState(),
                        upcomingSessionId = result.sessions.upcomingSessionId.orEmpty()
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
                val copySessions = state.sessions.map { session ->
                    if (session.id == sessionId) {
                        session.copy(isAttended = true)
                    } else {
                        session
                    }
                }

                reduce {
                    copy(
                        sessions = copySessions,
                        showAttendCodeBottomSheet = false
                    )
                }
            }.onFailure { it.record() }
        }
    }
}
