package com.yapp.feature.home.setting

data class SettingState(
    val isNotificationEnabled: Boolean = false,
    val showLogoutDialog: Boolean = false,
    val showDeleteAccountDialog: Boolean = false,
)

sealed interface SettingIntent {
    data object EnterScreen : SettingIntent
    data object ClickNotificationSwitch : SettingIntent
    data object ClickLogoutItem : SettingIntent
    data object ClickBackButton : SettingIntent
    data object ClickPrivacyPolicyItem : SettingIntent
    data object ClickTermsItem : SettingIntent
    data object ClickInquiryItem : SettingIntent
    data object ClickDeleteAccountItem : SettingIntent
    data object DismissLogoutDialog : SettingIntent
    data object ClickLogoutDialogCancelButton : SettingIntent
    data object ClickLogoutDialogLogoutButton : SettingIntent
    data object DismissDeleteAccountDialog : SettingIntent
    data object ClickDeleteAccountDialogCancelButton : SettingIntent
    data object ClickDeleteAccountDialogDeleteButton : SettingIntent
}

sealed interface SettingSideEffect {
    data object NavigateBack : SettingSideEffect
    data class OpenWebBrowser(val link: String) : SettingSideEffect
    data object NavigateToLogin : SettingSideEffect
}