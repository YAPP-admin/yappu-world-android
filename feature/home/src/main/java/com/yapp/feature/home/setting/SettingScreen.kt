package com.yapp.feature.home.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.button.outlined.YappOutlinedSecondaryButtonXLarge
import com.yapp.core.designsystem.component.control.switches.YappSwitchMedium
import com.yapp.core.designsystem.component.header.YappHeaderActionbarExpanded
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.borderBottom
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.home.R
import com.yapp.feature.home.setting.component.SettingItemLarge
import com.yapp.feature.home.setting.component.SettingItemMedium

@Composable
fun SettingRoute(
    viewModel: SettingViewModel = hiltViewModel()
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    viewModel.store.sideEffects.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SettingSideEffect.NavigateBack -> {
            }
            SettingSideEffect.OpenPrivacyPolicy -> {
            }
            SettingSideEffect.OpenTerms -> {
            }
            SettingSideEffect.OpenInquiry -> {
            }
        }
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
                                onIntent(SettingIntent.ClickNotificationSwitch(it))
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
    }
}


@Preview
@Composable
private fun SettingScreenPreview() {
    YappTheme {
        SettingScreen()
    }
}