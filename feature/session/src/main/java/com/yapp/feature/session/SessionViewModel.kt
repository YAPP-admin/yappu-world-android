package com.yapp.feature.session

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.ScheduleRepository
import com.yapp.domain.runCatchingIgnoreCancelled
import com.yapp.feature.session.navigation.SessionRoute
import com.yapp.model.exceptions.InvalidTokenException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SessionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val scheduleRepository: ScheduleRepository,
) : ViewModel() {
    private val sessionId: String = savedStateHandle.toRoute<SessionRoute>().sessionId

    val store: MviIntentStore<SessionState, SessionIntent, SessionSideEffect> =
        mviIntentStore(
            initialState = SessionState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: SessionIntent,
        state: SessionState,
        reduce: (SessionState.() -> SessionState) -> Unit,
        postSideEffect: (SessionSideEffect) -> Unit,
    ) {
        when (intent) {
            SessionIntent.EnterSessionScreen -> {
                if (state.isLoading.not()) return
                loadSessionDetail(reduce, postSideEffect)
            }
            is SessionIntent.ClickKakaoMap -> {
                postSideEffect(SessionSideEffect.OpenKakaoMap(intent.name, intent.latitude, intent.longitude))
            }
            is SessionIntent.ClickNaverMap -> {
                postSideEffect(SessionSideEffect.OpenNaverMap(intent.latitude, intent.longitude, intent.name))
            }
            is SessionIntent.ClickCopyAddress -> {
                postSideEffect(SessionSideEffect.CopyAddressToClipboard(intent.address))
                postSideEffect(SessionSideEffect.ShowToast("주소가 복사되었습니다"))
            }
            is SessionIntent.ClickNoticeItem -> {
                postSideEffect(SessionSideEffect.NavigateToNoticeDetail(intent.noticeId))
            }
        }
    }

    private fun loadSessionDetail(
        reduce: (SessionState.() -> SessionState) -> Unit,
        postSideEffect: (SessionSideEffect) -> Unit
    ) {
        viewModelScope.launch {
            reduce { copy(isLoading = true, error = null) }
            runCatchingIgnoreCancelled {
                scheduleRepository.getSessionDetail(sessionId)
            }.onSuccess { sessionDetail ->
                reduce {
                    copy(
                        sessionDetail = sessionDetail,
                        isLoading = false
                    )
                }
            }.onFailure { e ->
                when (e) {
                    is InvalidTokenException -> postSideEffect(SessionSideEffect.NavigateToLogin)
                    else -> {
                        reduce { copy(error = e, isLoading = false) }
                        postSideEffect(SessionSideEffect.HandleException(e))
                        e.record()
                    }
                }
            }
        }
    }
}

