package com.yapp.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.component.UserRole
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.domain.DeleteAccountUseCase
import com.yapp.domain.GetUserProfileUseCase
import com.yapp.domain.LogoutUseCase
import com.yapp.model.exceptions.InvalidTokenException
import com.yapp.model.exceptions.UserNotFoundForEmailException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val userDeleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {
    val store: MviIntentStore<ProfileState, ProfileIntent, ProfileSideEffect> =
        mviIntentStore(
            initialState = ProfileState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: ProfileIntent,
        state: ProfileState,
        reduce: (ProfileState.() -> ProfileState) -> Unit,
        sideEffect: (ProfileSideEffect) -> Unit
    ) {
        when (intent) {
            ProfileIntent.EntryScreen -> {
                onCollectGetUserProfile(
                    postSideEffect = sideEffect,
                    reduce = reduce
                )
            }
            ProfileIntent.CancelWithdraw -> {
                reduce {
                    copy(showWithDrawDialog = state.showWithDrawDialog.not())
                }
            }
            ProfileIntent.ClickUsage -> {
                sideEffect(ProfileSideEffect.NavigateToUsage)
            }
            ProfileIntent.ClickLogout -> {
                reduce {
                    copy(showLogoutDialog = state.showLogoutDialog.not())
                }
            }
            ProfileIntent.ClickSettings -> {
                sideEffect(ProfileSideEffect.NavigateToSetting)
            }
            ProfileIntent.ClickWithdraw -> {
                reduce {
                    copy(showWithDrawDialog = state.showWithDrawDialog.not())
                }
            }
            ProfileIntent.ClickAttendHistory -> {
                sideEffect(ProfileSideEffect.NavigateToAttendHistory)
            }
            ProfileIntent.ClickPreviousHistory -> {
                sideEffect(ProfileSideEffect.NavigateToPreviousHistory)
            }
            ProfileIntent.CancelLogout, ProfileIntent.DismissLogout -> {
                reduce {
                    copy(showLogoutDialog = state.showLogoutDialog.not())
                }
            }
            ProfileIntent.LaunchedLogout -> {
                viewModelScope.launch {
                    logoutUseCase().onSuccess {
                        sideEffect(ProfileSideEffect.NavigateToLogin)
                    }
                }
            }
            ProfileIntent.LaunchedWithdraw -> {
                viewModelScope.launch {
                    userDeleteAccountUseCase().onSuccess {
                        sideEffect(ProfileSideEffect.NavigateToLogin)
                    }
                }
            }
        }
    }

    private fun onCollectGetUserProfile(
        postSideEffect: (ProfileSideEffect) -> Unit,
        reduce: (ProfileState.() -> ProfileState) -> Unit
    ) {
        viewModelScope.launch {
            getUserProfileUseCase.invoke()
                .catch { exception ->
                    when(exception) {
                        is UserNotFoundForEmailException, is InvalidTokenException -> postSideEffect(ProfileSideEffect.NavigateToLogin)
                        else -> exception.record()
                    }
                }.collectLatest { result ->
                    reduce {
                        copy(
                            name = result.name,
                            role = UserRole.fromRole(result.role),
                            generation = result.activityUnits.firstOrNull()?.generation ?: 25,
                            position = result.activityUnits.firstOrNull()?.position ?: "Android"
                        )
                    }
                }
        }
    }
}
