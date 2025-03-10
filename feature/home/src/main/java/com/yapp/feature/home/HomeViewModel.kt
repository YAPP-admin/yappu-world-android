package com.yapp.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.ui.component.UserRole
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.PostsRepository
import com.yapp.domain.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getNoticeListRepository: PostsRepository,
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
        Log.d("HomeViewModel", "intent :: $intent")

        when (intent) {
            HomeIntent.ClickMoreButton -> postSideEffect(HomeSideEffect.NavigateToNotice)
            HomeIntent.ClickSettingButton -> postSideEffect(HomeSideEffect.NavigateToSetting)
            HomeIntent.EnterHomeScreen -> {
                loadUserInfo(state, reduce, postSideEffect)
            }
        }
    }

    private fun loadUserInfo(
        state: HomeState,
        reduce: (HomeState.() -> HomeState) -> Unit,
        postSideEffect: (HomeSideEffect) -> Unit,
    ) = viewModelScope.launch {
        reduce { copy(isLoading = true, isUserInfoLoading = true, isNoticesLoading = true) }
        getUserProfileUseCase()
            .collect { info ->
                reduce {
                    copy(
                        isUserInfoLoading = false,
                        name = info.name,
                        role = UserRole.fromRole(info.role),
                        activityUnits = info.activityUnits
                    )
                }
            }

        getNoticeListRepository.getNoticeList(noticeType = "전체", lastNoticeId = null, limit = 3)
            .collect { notices ->
                reduce { copy(noticeInfo = notices, isNoticesLoading = false) }
            }

        launch {
            combine(
                store.uiState.map { it.isUserInfoLoading },
                store.uiState.map { it.isNoticesLoading },
            ) { isUserInfoLoading, isNoticesLoading ->
                (isUserInfoLoading && isNoticesLoading)
            }.collect { isLoading ->
                reduce { copy(isLoading = isLoading) }
            }
        }
    }
}