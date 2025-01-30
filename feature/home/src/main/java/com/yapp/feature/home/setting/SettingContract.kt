package com.yapp.feature.home.setting

data class SettingState(
    val isNotificationEnabled: Boolean = false,
    val errorMessage: String? = null
)

sealed interface SettingIntent {
    data class ClickNotificationSwitch(val enabled: Boolean) : SettingIntent
    data object ClickLogoutButton : SettingIntent
    data object ClickBackButton : SettingIntent
    data object ClickPrivacyPolicyItem : SettingIntent
    data object ClickTermsItem : SettingIntent
    data object ClickInquiryItem : SettingIntent
    data object ClickDeleteAccountButton : SettingIntent
}

sealed interface SettingSideEffect {
    data object NavigateBack : SettingSideEffect
    data object OpenPrivacyPolicy : SettingSideEffect
    data object OpenTerms : SettingSideEffect
    data object OpenInquiry : SettingSideEffect
}