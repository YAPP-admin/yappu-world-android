package com.yapp.feature.setting

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.control.switches.YappSwitchMedium
import com.yapp.core.designsystem.component.header.YappHeaderActionbar
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.borderBottom
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.core.ui.extension.safeOpenUri
import com.yapp.feature.setting.component.SettingItemLarge
import com.yapp.feature.setting.component.SettingItemMedium
import com.yapp.core.ui.R as coreR

@Composable
fun SettingRoute(
    viewModel: SettingViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateLogin: () -> Unit,
    handleException: (Throwable) -> Unit,
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    viewModel.store.sideEffects.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SettingSideEffect.NavigateBack -> navigateBack()

            is SettingSideEffect.OpenWebBrowser -> {
                uriHandler.safeOpenUri(sideEffect.link)
            }

            SettingSideEffect.NavigateToLogin -> {
                navigateLogin()
            }

            is SettingSideEffect.ShowUrlLoadFailToast -> {
                Toast.makeText(context, context.getString(coreR.string.toast_message_error_loading_url), Toast.LENGTH_SHORT).show()
            }

            is SettingSideEffect.HandleException -> handleException(sideEffect.exception)
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
            YappHeaderActionbar(
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
                    style = YappTheme.typography.body1NormalBold,
                    color = YappTheme.colorScheme.labelNormal
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
                    text = stringResource(id = R.string.setting_screen_item_app_version),
                    onClick = {
//                        onIntent(SettingIntent.ClickAppVersion)
                    },
                    slot = {
                        Text(
                            text = uiState.appVersion,
                            style = YappTheme.typography.body1NormalRegular,
                            color = YappTheme.colorScheme.labelAlternative
                        )
                    }
                )
            }
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