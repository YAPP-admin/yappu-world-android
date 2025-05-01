package com.yapp.feature.home.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.AlarmRepository
import com.yapp.dataapi.OperationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val operationsRepository: OperationsRepository,
) : ViewModel() {
    private var privacyPolicyLink: String? = null
    private var termsLink: String? = null
    private var inquiryLink: String? = null

    val store: MviIntentStore<SettingState, SettingIntent, SettingSideEffect> =
        mviIntentStore(
            initialState = SettingState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: SettingIntent,
        state: SettingState,
        reduce: (SettingState.() -> SettingState) -> Unit,
        postSideEffect: (SettingSideEffect) -> Unit
    ) {
        when (intent) {
            SettingIntent.EnterScreen -> {
                viewModelScope.launch {
                    val enabled = alarmRepository.getMasterAlarmStatus()
                    val appVersion = operationsRepository.getAppVersion()
                    reduce { copy(isNotificationEnabled = enabled, appVersion = appVersion) }
                }

                updateUrl()
            }

            is SettingIntent.ClickNotificationSwitch -> {
                viewModelScope.launch {
                    val enabled = alarmRepository.updateMasterAlarmStatus()
                    reduce { copy(isNotificationEnabled = enabled) }
                }
            }

            SettingIntent.ClickBackButton -> {
                postSideEffect(SettingSideEffect.NavigateBack)
            }

            SettingIntent.ClickPrivacyPolicyItem -> {
                privacyPolicyLink?.let {
                    postSideEffect(SettingSideEffect.OpenWebBrowser(it))
                } ?: run {
                    updateUrl()
                    postSideEffect(SettingSideEffect.ShowUrlLoadFailToast)
                }
            }

            SettingIntent.ClickTermsItem -> {
                termsLink?.let {
                    postSideEffect(SettingSideEffect.OpenWebBrowser(it))
                } ?: run {
                    updateUrl()
                    postSideEffect(SettingSideEffect.ShowUrlLoadFailToast)
                }
            }

            SettingIntent.ClickInquiryItem -> {
                inquiryLink?.let {
                    postSideEffect(SettingSideEffect.OpenWebBrowser(it))
                } ?: run {
                    updateUrl()
                    postSideEffect(SettingSideEffect.ShowUrlLoadFailToast)
                }
            }
        }
    }

    private fun updateUrl() = viewModelScope.launch {
        val privacyPolicyDeferred = async {
            if (privacyPolicyLink == null) {
                runCatching { operationsRepository.getPrivacyPolicyLink() }
            } else {
                Result.success(privacyPolicyLink)
            }
        }
        val termsDeferred = async {
            if (termsLink == null) {
                runCatching { operationsRepository.getTermsOfServiceLink() }
            } else {
                Result.success(termsLink)
            }
        }
        val inquiryDeferred = async {
            if (inquiryLink == null) {
                runCatching { operationsRepository.getUsageInquiryLink() }
            } else {
                Result.success(inquiryLink)
            }
        }

        privacyPolicyLink = privacyPolicyDeferred.await().getOrNull()
        termsLink = termsDeferred.await().getOrNull()
        inquiryLink = inquiryDeferred.await().getOrNull()
    }

}
