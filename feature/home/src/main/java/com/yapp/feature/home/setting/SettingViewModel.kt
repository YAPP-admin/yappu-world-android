package com.yapp.feature.home.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.AlarmRepository
import com.yapp.dataapi.OperationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val operationsRepository: OperationsRepository,
) : ViewModel() {
    private var privacyPolicyLink = ""
    private var termsLink = ""
    private var inquiryLink = ""

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

                combine(
                    operationsRepository.getPrivacyPolicyLink(),
                    operationsRepository.getTermsOfServiceLink(),
                    operationsRepository.getUsageInquiryLink(),
                ) { privacyPolicyLink, termsLink, inquiryLink ->
                    Triple(privacyPolicyLink, termsLink, inquiryLink)
                }.onEach { (privacyPolicyLink, termsLink, inquiryLink) ->
                    this@SettingViewModel.privacyPolicyLink = privacyPolicyLink
                    this@SettingViewModel.termsLink = termsLink
                    this@SettingViewModel.inquiryLink = inquiryLink
                }.catch {
                    it.record()
                }.launchIn(viewModelScope)
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
                postSideEffect(SettingSideEffect.OpenWebBrowser(privacyPolicyLink))
            }

            SettingIntent.ClickTermsItem -> {
                postSideEffect(SettingSideEffect.OpenWebBrowser(termsLink))
            }

            SettingIntent.ClickInquiryItem -> {
                postSideEffect(SettingSideEffect.OpenWebBrowser(inquiryLink))
            }
        }
    }
}
