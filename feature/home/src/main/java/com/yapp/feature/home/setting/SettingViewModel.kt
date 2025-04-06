package com.yapp.feature.home.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.AlarmRepository
import com.yapp.dataapi.OperationsRepository
import com.yapp.domain.DeleteAccountUseCase
import com.yapp.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val alarmRepository: AlarmRepository,
    private val deleteAccountUseCase: DeleteAccountUseCase,
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
                    reduce { copy(isNotificationEnabled = enabled) }
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

            SettingIntent.ClickLogoutItem -> {
                reduce { copy(showLogoutDialog = true) }
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

            SettingIntent.ClickDeleteAccountItem -> {
                reduce { copy(showDeleteAccountDialog = true) }
            }

            SettingIntent.DismissDeleteAccountDialog,
            SettingIntent.ClickDeleteAccountDialogCancelButton -> {
                reduce { copy(showDeleteAccountDialog = false) }
            }

            SettingIntent.DismissLogoutDialog,
            SettingIntent.ClickLogoutDialogCancelButton -> {
                reduce { copy(showLogoutDialog = false) }
            }

            SettingIntent.ClickDeleteAccountDialogDeleteButton -> {
                viewModelScope.launch {
                    deleteAccountUseCase()
                    postSideEffect(SettingSideEffect.NavigateToLogin)
                }
            }

            SettingIntent.ClickLogoutDialogLogoutButton -> {
                viewModelScope.launch {
                    logoutUseCase()
                    postSideEffect(SettingSideEffect.NavigateToLogin)
                }
            }
        }
    }
}
