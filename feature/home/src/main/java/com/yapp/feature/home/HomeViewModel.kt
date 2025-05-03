package com.yapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.domain.SessionsUseCase
import com.yapp.domain.runCatchingIgnoreCancelled
import com.yapp.feature.home.convert.toState
import com.yapp.model.exceptions.InvalidTokenException
import com.yapp.model.exceptions.UserNotFoundForEmailException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val sessionsUseCase: SessionsUseCase
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
                val upcomingSessions = result.upcomingSessionInfo
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
}
