package com.yapp.feature.home.setting

data class SettingState(
    val isNotificationEnabled: Boolean = false,
    val showLogoutDialog: Boolean = false,
    val showDeleteAccountDialog: Boolean = false,
)

sealed interface SettingIntent {
    data object EnterScreen : SettingIntent
    data object ClickNotificationSwitch : SettingIntent
    data object ClickLogoutButton : SettingIntent
    data object ClickBackButton : SettingIntent
    data object ClickPrivacyPolicyItem : SettingIntent
    data object ClickTermsItem : SettingIntent
    data object ClickInquiryItem : SettingIntent
    data object ClickDeleteAccountButton : SettingIntent
    data object DismissLogoutDialog : SettingIntent
    data object ClickLogoutDialogActionButton : SettingIntent
    data object ClickLogoutDialogRecommendActionButton : SettingIntent
    data object DismissDeleteAccountDialog : SettingIntent
    data object ClickDeleteAccountDialogActionButton : SettingIntent
    data object ClickDeleteAccountDialogRecommendActionButton : SettingIntent
}

sealed interface SettingSideEffect {
    data object NavigateBack : SettingSideEffect
    data object OpenPrivacyPolicy : SettingSideEffect
    data object OpenTerms : SettingSideEffect
    data object OpenInquiry : SettingSideEffect
    data object NavigateToLogin : SettingSideEffect
}