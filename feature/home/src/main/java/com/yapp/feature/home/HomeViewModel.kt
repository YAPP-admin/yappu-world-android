package com.yapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.model.exceptions.InvalidTokenException
import com.yapp.model.exceptions.UserNotFoundForEmailException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val sessionsUseCase: SessionsUseCase,
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
    ) = viewModelScope.launch {
        sessionsUseCase.invoke()
            .catch { error ->
                when (error) {
                    is UserNotFoundForEmailException, is InvalidTokenException -> postSideEffect(HomeSideEffect.NavigateToLogin)
                    else -> error.record()
                }
            }.collectLatest { sessions ->
                reduce {
                    copy(
                        sessions = sessions
                    )
                }
            }
    }
}
