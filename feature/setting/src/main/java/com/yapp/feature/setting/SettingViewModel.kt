package com.yapp.feature.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.AlarmRepository
import com.yapp.dataapi.OperationsRepository
import com.yapp.domain.runCatchingIgnoreCancelled
import com.yapp.model.exceptions.InvalidTokenException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val operationsRepository: OperationsRepository,
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
                    runCatchingIgnoreCancelled {
                        val enabled = alarmRepository.getMasterAlarmStatus()
                        val appVersion = operationsRepository.getAppVersion()
                        reduce { copy(isNotificationEnabled = enabled, appVersion = appVersion) }
                    }.onFailure { e ->
                        when (e) {
                            is InvalidTokenException -> {
                                postSideEffect(SettingSideEffect.NavigateToLogin)
                            }
                            else -> {
                                postSideEffect(SettingSideEffect.HandleException(e))
                                e.record()
                            }
                        }
                    }
                }
            }

            is SettingIntent.ClickNotificationSwitch -> {
                viewModelScope.launch {
                    runCatchingIgnoreCancelled {
                        val enabled = alarmRepository.updateMasterAlarmStatus()
                        reduce { copy(isNotificationEnabled = enabled) }
                    }.onFailure { e ->
                        when (e) {
                            is InvalidTokenException -> {
                                postSideEffect(SettingSideEffect.NavigateToLogin)
                            }
                            else -> {
                                postSideEffect(SettingSideEffect.HandleException(e))
                                e.record()
                            }
                        }
                    }
                }
            }

            SettingIntent.ClickBackButton -> {
                postSideEffect(SettingSideEffect.NavigateBack)
            }

            SettingIntent.ClickPrivacyPolicyItem -> {
                viewModelScope.launch {
                    runCatchingIgnoreCancelled { operationsRepository.getPrivacyPolicyLink() }
                        .onSuccess { postSideEffect(SettingSideEffect.OpenWebBrowser(it)) }
                        .onFailure { postSideEffect(SettingSideEffect.ShowUrlLoadFailToast) }
                }
            }

            SettingIntent.ClickTermsItem -> {
                viewModelScope.launch {
                    runCatchingIgnoreCancelled { operationsRepository.getTermsOfServiceLink() }
                        .onSuccess { postSideEffect(SettingSideEffect.OpenWebBrowser(it)) }
                        .onFailure { postSideEffect(SettingSideEffect.ShowUrlLoadFailToast) }
                }
            }
        }
    }

}
