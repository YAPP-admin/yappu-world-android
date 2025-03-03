package com.yapp.feature.home.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.AlarmRepository
import com.yapp.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val alarmRepository: AlarmRepository,
) : ViewModel() {

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
            }

            is SettingIntent.ClickNotificationSwitch -> {
                viewModelScope.launch {
                    val enabled = alarmRepository.updateMasterAlarmStatus()
                    reduce { copy(isNotificationEnabled = enabled) }
                }
            }
            SettingIntent.ClickLogoutButton -> {
                reduce { copy(showLogoutDialog = true) }
            }
            SettingIntent.ClickBackButton -> {
                postSideEffect(SettingSideEffect.NavigateBack)
            }
            SettingIntent.ClickPrivacyPolicyItem -> {
                postSideEffect(SettingSideEffect.OpenPrivacyPolicy)
            }
            SettingIntent.ClickTermsItem -> {
                postSideEffect(SettingSideEffect.OpenTerms)
            }
            SettingIntent.ClickInquiryItem -> {
                postSideEffect(SettingSideEffect.OpenInquiry)
            }
            SettingIntent.ClickDeleteAccountButton -> {
                reduce { copy(showDeleteAccountDialog = true) }
            }

            SettingIntent.DismissDeleteAccountDialog,
            SettingIntent.ClickDeleteAccountDialogActionButton -> {
                reduce { copy(showDeleteAccountDialog = false) }
            }

            SettingIntent.DismissLogoutDialog,
            SettingIntent.ClickLogoutDialogActionButton -> {
                reduce { copy(showLogoutDialog = false) }
            }

            SettingIntent.ClickDeleteAccountDialogRecommendActionButton -> TODO()

            SettingIntent.ClickLogoutDialogRecommendActionButton -> {
                viewModelScope.launch {
                    logoutUseCase()
                    postSideEffect(SettingSideEffect.NavigateToLogin)
                }
            }

        }
    }
}
