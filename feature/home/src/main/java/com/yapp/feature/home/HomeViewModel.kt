package com.yapp.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.ui.component.UserRole
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.PostsRepository
import com.yapp.domain.GetUserProfileUseCase
import com.yapp.model.NoticeType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
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
            HomeIntent.EnterHomeScreen -> { loadHomeInfo( reduce)
            }
        }
    }

    private fun loadHomeInfo(
        reduce: (HomeState.() -> HomeState) -> Unit,
    ) = viewModelScope.launch {
        reduce { copy(isLoading = true, isUserInfoLoading = true, isNoticesLoading = true) }
        getUserProfileUseCase()
            .collectLatest{ userInfo ->
                reduce {
                    copy(
                        isUserInfoLoading = false,
                        name = userInfo.name,
                        role = UserRole.fromRole(userInfo.role),
                        activityUnits = userInfo.activityUnits
                    )
                }
            }

        getNoticeListRepository.getNoticeList(
            noticeType = NoticeType.ALL.apiValue,
            lastNoticeId = null,
            limit = 3
        ).collectLatest{ noticeInfo ->
            reduce { copy(noticeInfo = noticeInfo, isNoticesLoading = false) }
        }

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