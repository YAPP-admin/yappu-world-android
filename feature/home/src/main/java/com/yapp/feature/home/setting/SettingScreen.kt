package com.yapp.feature.home.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.alert.YappAlertDialog
import com.yapp.core.designsystem.component.button.outlined.YappOutlinedSecondaryButtonXLarge
import com.yapp.core.designsystem.component.control.switches.YappSwitchMedium
import com.yapp.core.designsystem.component.header.YappHeaderActionbarExpanded
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.constant.Url
import com.yapp.core.ui.extension.borderBottom
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.home.R
import com.yapp.feature.home.setting.component.SettingItemLarge
import com.yapp.feature.home.setting.component.SettingItemMedium

@Composable
fun SettingRoute(
    viewModel: SettingViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    viewModel.store.sideEffects.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SettingSideEffect.NavigateBack -> navigateBack()

            SettingSideEffect.OpenPrivacyPolicy -> {
                Url.moveToUrl(context, Url.PRIVACY_POLICY)
            }

            SettingSideEffect.OpenTerms -> {
                Url.moveToUrl(context, Url.TERMS)
            }

            SettingSideEffect.OpenInquiry -> {
            }

            SettingSideEffect.NavigateToLogin -> {
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.store.onIntent(SettingIntent.EnterScreen)
    }

    SettingScreen(
        uiState = uiState,
        onIntent = { viewModel.store.onIntent(it) }
    )
}


@Composable
fun SettingScreen(
    uiState: SettingState = SettingState(),
    onIntent: (SettingIntent) -> Unit = {}
) {
    YappBackground {
        Column {
            YappHeaderActionbarExpanded(
                title = stringResource(id = R.string.setting_screen_title),
                leftIcon = com.yapp.core.designsystem.R.drawable.icon_chevron_left,
                contentDescription = stringResource(id = R.string.setting_screen_back_icon_content_description),
                onClickLeftIcon = {
                    onIntent(SettingIntent.ClickBackButton)
                },
            )

            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                SettingItemLarge(
                    modifier = Modifier
                        .borderBottom(
                            color = YappTheme.colorScheme.lineNormalAlternative,
                        ),
                    text = stringResource(id = R.string.setting_screen_item_notification),
                    slot = {
                        YappSwitchMedium(
                            checked = uiState.isNotificationEnabled,
                            onCheckedChange = {
                                onIntent(SettingIntent.ClickNotificationSwitch)
                            }
                        )
                    }
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.setting_screen_item_terms),
                    color = YappTheme.colorScheme.labelNeutral,
                    style = YappTheme.typography.headline1Bold
                )

                SettingItemMedium(
                    modifier = Modifier
                        .borderBottom(
                            color = YappTheme.colorScheme.lineNormalAlternative,
                        ),
                    text = stringResource(id = R.string.setting_screen_item_privacy_policy),
                    onClick = {
                        onIntent(SettingIntent.ClickPrivacyPolicyItem)
                    },
                    slot = {
                        Icon(
                            painter = painterResource(id = com.yapp.core.designsystem.R.drawable.icon_chevron_right),
                            contentDescription = null,
                            tint = YappTheme.colorScheme.labelAssistive,
                        )
                    }
                )

                SettingItemMedium(
                    modifier = Modifier
                        .borderBottom(
                            color = YappTheme.colorScheme.lineNormalAlternative,
                        ),
                    text = stringResource(id = R.string.setting_screen_item_terms),
                    onClick = {
                        onIntent(SettingIntent.ClickTermsItem)
                    },
                    slot = {
                        Icon(
                            painter = painterResource(id = com.yapp.core.designsystem.R.drawable.icon_chevron_right),
                            contentDescription = null,
                            tint = YappTheme.colorScheme.labelAssistive,
                        )
                    }
                )

                SettingItemLarge(
                    modifier = Modifier
                        .borderBottom(
                            color = YappTheme.colorScheme.lineNormalAlternative,
                        ),
                    text = stringResource(id = R.string.setting_screen_item_inquiry),
                    onClick = {
                        onIntent(SettingIntent.ClickInquiryItem)
                    },
                    slot = {
                        Icon(
                            painter = painterResource(id = com.yapp.core.designsystem.R.drawable.icon_chevron_right),
                            contentDescription = null,
                            tint = YappTheme.colorScheme.labelAssistive,
                        )
                    }
                )

                SettingItemLarge(
                    text = stringResource(id = R.string.setting_screen_item_delete_account),
                    onClick = {
                        onIntent(SettingIntent.ClickDeleteAccountButton)
                    }
                )
            }

            Spacer(Modifier.weight(1f))

            YappOutlinedSecondaryButtonXLarge(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.setting_screen_item_logout),
                onClick = {
                    onIntent(SettingIntent.ClickLogoutButton)
                }
            )

            Spacer(Modifier.height(16.dp))
        }

        if (uiState.showLogoutDialog) {
            YappAlertDialog(
                title = stringResource(R.string.logout_dialog_title),
                actionButtonText = stringResource(R.string.logout_dialog_action_button),
                recommendActionButtonText = stringResource(R.string.logout_dialog_recommend_action_button),
                onDismissRequest = { onIntent(SettingIntent.DismissLogoutDialog) },
                onActionButtonClick = { onIntent(SettingIntent.ClickLogoutDialogActionButton) },
                onRecommendActionButtonClick = { onIntent(SettingIntent.ClickLogoutDialogRecommendActionButton) },
            )
        }

        if (uiState.showDeleteAccountDialog) {
            YappAlertDialog(
                title = stringResource(R.string.delete_account_dialog_title),
                body = stringResource(R.string.delete_account_dialog_body),
                actionButtonText = stringResource(R.string.delete_account_dialog_action_button),
                recommendActionButtonText = stringResource(R.string.delete_account_dialog_recommend_action_button),
                onDismissRequest = { onIntent(SettingIntent.DismissDeleteAccountDialog) },
                onActionButtonClick = { onIntent(SettingIntent.ClickDeleteAccountDialogActionButton) },
                onRecommendActionButtonClick = { onIntent(SettingIntent.ClickDeleteAccountDialogRecommendActionButton) },
            )
        }
    }
}


@Preview
@Composable
private fun SettingScreenPreview() {
    YappTheme {
        SettingScreen()
    }
}