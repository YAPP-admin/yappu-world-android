package com.yapp.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.ui.component.UserRole
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.domain.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
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
            HomeIntent.LoadMyInfo -> {
                loadUserInfo(reduce, postSideEffect)
            }
        }
    }

    private fun loadUserInfo(
        reduce: (HomeState.() -> HomeState) -> Unit,
        postSideEffect: (HomeSideEffect) -> Unit,
    ) = viewModelScope.launch {
        getUserProfileUseCase()
            .onSuccess { info ->
                reduce {
                    copy(
                        name = info.name,
                        role = UserRole.fromRole(info.role),
                        activityUnits = info.activityUnits
                    )
                }
            }
            .onFailure {
                val errorMessage = it.message ?: ""
                postSideEffect(HomeSideEffect.ShowToast(errorMessage))
            }
    }
}