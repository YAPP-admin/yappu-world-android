package com.yapp.feature.setting

data class SettingState(
    val isNotificationEnabled: Boolean = false,
    val appVersion: String = ""
)

sealed interface SettingIntent {
    data object EnterScreen : SettingIntent
    data object ClickNotificationSwitch : SettingIntent
    data object ClickBackButton : SettingIntent
    data object ClickPrivacyPolicyItem : SettingIntent
    data object ClickTermsItem : SettingIntent
}

sealed interface SettingSideEffect {
    data object NavigateBack : SettingSideEffect
    data class OpenWebBrowser(val link: String) : SettingSideEffect
    data object ShowUrlLoadFailToast : SettingSideEffect
    data object NavigateToLogin : SettingSideEffect
    data class HandleException(val exception: Throwable) : SettingSideEffect
}