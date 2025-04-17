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
    data object OnEntryScreen : ProfileIntent
    data object OnClickSettings : ProfileIntent
    data object OnClickAttendHistory : ProfileIntent
    data object OnClickPreviousHistory : ProfileIntent
    data object OnClickUsage : ProfileIntent
    data object OnClickWithdraw : ProfileIntent
    data object OnClickLogout : ProfileIntent
    data object OnCancelLogout : ProfileIntent
    data object OnDismissLogout : ProfileIntent
    data object OnLaunchedLogout : ProfileIntent
}

sealed interface ProfileSideEffect {
    data object NavigateToSetting : ProfileSideEffect
    data object NavigateToAttendHistory : ProfileSideEffect
    data object NavigateToPreviousHistory : ProfileSideEffect
    data object NavigateToLogin : ProfileSideEffect
}
