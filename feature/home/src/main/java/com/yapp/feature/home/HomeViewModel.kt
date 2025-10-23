package com.yapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.common.android.util.toMonthDateRange
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.AttendanceRepository
import com.yapp.dataapi.PostsRepository
import com.yapp.dataapi.ScheduleRepository
import com.yapp.domain.runCatchingIgnoreCancelled
import com.yapp.model.AttendanceInfo
import com.yapp.model.AttendanceStatus
import com.yapp.model.HomeSessionList
import com.yapp.model.NoticeType
import com.yapp.model.exceptions.CodeNotCorrectException
import com.yapp.model.exceptions.InvalidTokenException
import com.yapp.model.exceptions.NoScheduledSessionException
import com.yapp.model.exceptions.NotFoundException
import com.yapp.model.exceptions.UndefineNoticeWriterInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val attendanceRepository: AttendanceRepository,
    private val postsRepository: PostsRepository,
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

                loadNoticeHistory(reduce, postSideEffect)

                viewModelScope.launch {
                    joinAll(
                        loadSessionInfo(reduce, postSideEffect),
                        loadUpcomingSessionInfo(reduce, postSideEffect),
                    )
                    isInitialized = true
                }
            }

            HomeIntent.Refresh -> {
                loadUpcomingSessionInfo(reduce, postSideEffect)
                loadNoticeHistory(reduce, postSideEffect)
            }

            is HomeIntent.ClickSessionItem -> postSideEffect(HomeSideEffect.NavigateToSessionDetail(intent.sessionId))
            HomeIntent.ClickShowAllSession -> postSideEffect(HomeSideEffect.NavigateToSchedule)
            HomeIntent.ClickShowAllNotice -> postSideEffect(HomeSideEffect.NavigateToNotice)
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
        val (startDate, endDate) = LocalDate.now().toMonthDateRange()

        reduce { copy(isLoading = true) }
        runCatchingIgnoreCancelled {
            scheduleRepository.getSessions(startDate, endDate)
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

    private fun loadNoticeHistory(
        reduce: (HomeState.() -> HomeState) -> Unit,
        postSideEffect: (HomeSideEffect) -> Unit
    ) {
        postsRepository.getNoticeList(null, 3, NoticeType.ALL.apiValue)
            .onEach { response ->
                reduce {
                    copy(notices = response.copy(
                        notices = response.notices
                    ))
                }
            }.catch { exception ->
                when (exception) {
                    is InvalidTokenException -> postSideEffect(HomeSideEffect.NavigateToLogin)
                    is UndefineNoticeWriterInfo -> { }
                    else -> {
                        postSideEffect(HomeSideEffect.HandleException(exception))
                        exception.record()
                    }
                }
            }.launchIn(viewModelScope)
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
