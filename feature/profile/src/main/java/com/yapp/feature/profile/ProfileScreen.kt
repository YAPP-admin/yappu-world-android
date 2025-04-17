package com.yapp.feature.profile

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.alert.YappAlertDialog
import com.yapp.core.designsystem.component.button.outlined.YappOutlinedSecondaryButtonLarge
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.feature.profile.component.ProfileInformationSection
import com.yapp.feature.profile.component.ProfileSectionItem
import com.yapp.feature.profile.component.ProfileTopBarSection
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.yapp.feature.profile.ProfileSideEffect as SideEffect

@Composable
internal fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToSettings: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.store.onIntent(ProfileIntent.OnEntryScreen)
    }

    LaunchedEffect(viewModel.store.sideEffects) {
        viewModel.store.sideEffects.onEach { effect ->
            when (effect) {
                SideEffect.NavigateToSetting -> {
                    onNavigateToSettings()
                }
                SideEffect.NavigateToAttendHistory -> {

                }
                SideEffect.NavigateToPreviousHistory -> {

                }
                SideEffect.NavigateToLogin -> {

                }
            }
        }.launchIn(scope)
    }

    if (uiState.showLogoutDialog) {
        YappAlertDialog(
            title = "로그아웃 할까요?",
            actionButtonText = "아니요!",
            recommendActionButtonText = "로그아웃",
            onDismissRequest = { viewModel.store.onIntent(ProfileIntent.OnDismissLogout) },
            onActionButtonClick = { viewModel.store.onIntent(ProfileIntent.OnCancelLogout) },
            onRecommendActionButtonClick = { viewModel.store.onIntent(ProfileIntent.OnLaunchedLogout) },
        )
    }

    ProfileScreen(
        userName = uiState.name,
        userGeneration = "${uiState.generation}기",
        userPosition = uiState.position,
        userRole = uiState.role.role,
        onClickSettings = { viewModel.store.onIntent(ProfileIntent.OnClickSettings) },
        onClickPreviousHistory = {},
        onClickAttendHistory = {},
        onClickQuestion = {},
        onClickWithdraw = {},
        onCLickLogout = { viewModel.store.onIntent(ProfileIntent.OnClickLogout) }
    )
}


@Composable
private fun ProfileScreen(
    userName: String,
    userGeneration: String,
    userPosition: String,
    userRole: String,
    onClickSettings: () -> Unit,
    onClickAttendHistory: () -> Unit,
    onClickPreviousHistory: () -> Unit,
    onClickQuestion: () -> Unit,
    onClickWithdraw: () -> Unit,
    onCLickLogout: () -> Unit
) {
    YappBackground {
        Column {
            Column(modifier = Modifier.weight(1f)) {
                ProfileTopBarSection(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .height(56.dp),
                    onClickSettings = onClickSettings
                )
                ProfileInformationSection(
                    userName = userName,
                    userGeneration = userGeneration,
                    userPosition = userPosition,
                    userRole = userRole
                )
                HorizontalDivider(thickness = 12.dp, color = YappTheme.colorScheme.lineNormalNormal)
                Spacer(modifier = Modifier.height(12.dp))
                ProfileSectionItem(
                    onClickItem = onClickAttendHistory,
                    title = "출석내역",
                    slot = {
                        Icon(
                            painter = painterResource(id = com.yapp.core.designsystem.R.drawable.icon_chevron_right),
                            contentDescription = null,
                            tint = YappTheme.colorScheme.labelAssistive
                        )
                    }
                )
                ProfileSectionItem(
                    onClickItem = onClickPreviousHistory,
                    title = "이전 활동 내역",
                    slot = {
                        Icon(
                            painter = painterResource(id = com.yapp.core.designsystem.R.drawable.icon_chevron_right),
                            contentDescription = null,
                            tint = YappTheme.colorScheme.labelAssistive
                        )
                    }
                )
                ProfileSectionItem(
                    onClickItem = onClickQuestion,
                    title = "이용문의",
                    slot = {
                        Icon(
                            painter = painterResource(id = com.yapp.core.designsystem.R.drawable.icon_chevron_right),
                            contentDescription = null,
                            tint = YappTheme.colorScheme.labelAssistive
                        )
                    }
                )
                ProfileSectionItem(
                    onClickItem = onClickWithdraw,
                    title = "회원탈퇴"
                )
            }
            Column {
                YappOutlinedSecondaryButtonLarge(
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                    text = "로그아웃",
                    onClick = onCLickLogout
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
            onClickQuestion = {},
            onClickSettings = {},
            onClickAttendHistory = {},
            onClickPreviousHistory = {},
            onCLickLogout = {},
            onClickWithdraw = {}
        )
    }
}

