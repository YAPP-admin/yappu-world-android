package com.yapp.feature.profile

import com.yapp.core.ui.component.UserRole

data class ProfileState(
    val name: String = "",
    val role: UserRole = UserRole.ACTIVE,
    val generation: Int = 0,
    val position: String = "",
    val showLogoutDialog: Boolean = false,
    val showWithDrawDialog: Boolean = false
)

sealed interface ProfileIntent {
    data object EntryScreen : ProfileIntent
    data object ClickSettings : ProfileIntent
    data object ClickAttendHistory : ProfileIntent
    data object ClickPreviousHistory : ProfileIntent
    data object ClickUsage : ProfileIntent
    data object ClickWithdraw : ProfileIntent
    data object ClickLogout : ProfileIntent
    data object CancelLogout : ProfileIntent
    data object DismissLogout : ProfileIntent
    data object LaunchedLogout : ProfileIntent
    data object LaunchedWithdraw : ProfileIntent
    data object CancelWithdraw : ProfileIntent
}

sealed interface ProfileSideEffect {
    data object NavigateToSetting : ProfileSideEffect
    data object NavigateToAttendHistory : ProfileSideEffect
    data object NavigateToPreviousHistory : ProfileSideEffect
    data object NavigateToLogin : ProfileSideEffect
    data object NavigateToUsage : ProfileSideEffect
}
