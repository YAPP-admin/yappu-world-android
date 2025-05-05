package com.yapp.feature.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.yapp.core.designsystem.component.alert.YappAlertShortDialog
import com.yapp.core.designsystem.component.button.outlined.YappOutlinedSecondaryButtonLarge
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.core.ui.extension.safeOpenUri
import com.yapp.feature.profile.component.ProfileInformationSection
import com.yapp.feature.profile.component.ProfileSectionItem
import com.yapp.feature.profile.component.ProfileTopBarSection
import com.yapp.feature.profile.ProfileSideEffect as SideEffect

@Composable
internal fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToSettings: () -> Unit,
    onNavigateToAttendHistory: () -> Unit,
    onNavigateToPreviousHistory: () -> Unit,
    onNavigateToLogin: () -> Unit,
    handleException: (Throwable) -> Unit,
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        viewModel.store.onIntent(ProfileIntent.EntryScreen)
    }

    viewModel.store.sideEffects.collectWithLifecycle { effect ->
        when (effect) {
            SideEffect.NavigateToSetting -> {
                onNavigateToSettings()
            }
            SideEffect.NavigateToAttendHistory -> {
                onNavigateToAttendHistory()
            }
            SideEffect.NavigateToPreviousHistory -> {
                onNavigateToPreviousHistory()
            }
            SideEffect.NavigateToLogin -> {
                onNavigateToLogin()
            }
            is SideEffect.OpenWebBrowser -> {
                uriHandler.safeOpenUri(effect.link)
            }
            SideEffect.ShowUrlLoadFailToast -> {
                Toast.makeText(
                    context,
                    context.getString(com.yapp.core.ui.R.string.toast_message_error_loading_url),
                    Toast.LENGTH_SHORT
                ).show()
            }

            is SideEffect.HandleException -> {
                handleException(effect.exception)
            }
        }
    }

    if (uiState.showLogoutDialog) {
        YappAlertShortDialog(
            title = stringResource(R.string.profile_logout_title),
            actionButtonText = stringResource(R.string.profile_logout_action_button),
            recommendActionButtonText = stringResource(R.string.profile_logout_recommend_button),
            onDismissRequest = { viewModel.store.onIntent(ProfileIntent.DismissLogout) },
            onActionButtonClick = { viewModel.store.onIntent(ProfileIntent.CancelLogout) },
            onRecommendActionButtonClick = { viewModel.store.onIntent(ProfileIntent.LaunchedLogout) },
        )
    }

    if (uiState.showWithDrawDialog) {
        YappAlertShortDialog(
            title = stringResource(R.string.profile_withdraw_dialog_title),
            body = stringResource(R.string.profile_withdraw_dialog_message),
            actionButtonText = stringResource(R.string.profile_logout_action_button),
            recommendActionButtonText = stringResource(R.string.profile_withdraw_dialog_recommend_button),
            onDismissRequest = { viewModel.store.onIntent(ProfileIntent.CancelWithdraw) },
            onActionButtonClick = { viewModel.store.onIntent(ProfileIntent.CancelWithdraw) },
            onRecommendActionButtonClick = { viewModel.store.onIntent(ProfileIntent.LaunchedWithdraw) }
        )
    }

    ProfileScreen(
        userName = uiState.name,
        userGeneration = "${uiState.generation}기",
        userPosition = uiState.position,
        userRole = uiState.role.role,
        onIntent = viewModel.store::onIntent
    )
}


@Composable
private fun ProfileScreen(
    userName: String,
    userGeneration: String,
    userPosition: String,
    userRole: String,
    onIntent: (ProfileIntent) -> Unit
) {
    YappBackground {
        Column {
            Column(modifier = Modifier.weight(1f)) {
                ProfileTopBarSection(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .height(56.dp),
                    onClickSettings = { onIntent(ProfileIntent.ClickSettings) }
                )
                ProfileInformationSection(
                    userName = userName,
                    userGeneration = userGeneration,
                    userPosition = userPosition,
                    userRole = userRole
                )
                HorizontalDivider(thickness = 12.dp, color = YappTheme.colorScheme.lineNormalNormal.copy(alpha = 0.08f))
                Spacer(modifier = Modifier.height(12.dp))
                ProfileSectionItem(
                    onClickItem = { onIntent(ProfileIntent.ClickAttendHistory) },
                    title = stringResource(R.string.profile_attend_title),
                    slot = {
                        Icon(
                            painter = painterResource(id = com.yapp.core.designsystem.R.drawable.icon_chevron_right),
                            contentDescription = null,
                            tint = YappTheme.colorScheme.labelAssistive
                        )
                    }
                )
                ProfileSectionItem(
                    onClickItem = { onIntent(ProfileIntent.ClickPreviousHistory) },
                    title = stringResource(R.string.profile_previous_history),
                    slot = {
                        Icon(
                            painter = painterResource(id = com.yapp.core.designsystem.R.drawable.icon_chevron_right),
                            contentDescription = null,
                            tint = YappTheme.colorScheme.labelAssistive
                        )
                    }
                )
                ProfileSectionItem(
                    onClickItem = { onIntent(ProfileIntent.ClickUsage) },
                    title = stringResource(R.string.profile_question_title),
                    slot = {
                        Icon(
                            painter = painterResource(id = com.yapp.core.designsystem.R.drawable.icon_chevron_right),
                            contentDescription = null,
                            tint = YappTheme.colorScheme.labelAssistive
                        )
                    }
                )
                ProfileSectionItem(
                    onClickItem = { onIntent(ProfileIntent.ClickWithdraw) },
                    title = stringResource(R.string.profile_withdraw_title)
                )
            }
            Column {
                YappOutlinedSecondaryButtonLarge(
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                    text = stringResource(R.string.profile_logout_button),
                    onClick = { onIntent(ProfileIntent.ClickLogout) }
                )
            }
        }
    }
}

@Preview()
@Composable
private fun ProfileRoutePreview() {
    YappTheme {
        ProfileScreen(
            userName = "김야뿌",
            userGeneration = "25기",
            userPosition = "Android",
            userRole = "ADMIN",
            onIntent = {}
        )
    }
}

