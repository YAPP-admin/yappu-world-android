package com.yapp.feature.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.header.YappDefaultHeader
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.home.component.NoticeSection
import com.yapp.feature.home.component.ProfileLoadingSection
import com.yapp.feature.home.component.ProfileSection

@Composable
internal fun HomeRoute(
    navigateToNotice: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToNoticeDetail: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.store.onIntent(HomeIntent.EnterHomeScreen)
    }

    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    viewModel.store.sideEffects.collectWithLifecycle { effect ->
        when (effect) {
            HomeSideEffect.NavigateToNotice -> navigateToNotice()
            HomeSideEffect.NavigateToSetting -> navigateToSetting()
            is HomeSideEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }

            HomeSideEffect.NavigateToLogin -> navigateToLogin()
            is HomeSideEffect.NavigateToNoticeDetail -> navigateToNoticeDetail(effect.noticeId)
        }
    }

    HomeScreen(
        homeState = uiState,
        onIntent = { viewModel.store.onIntent(it) }
    )
}

@Composable
fun HomeScreen(
    homeState: HomeState,
    onIntent: (HomeIntent) -> Unit = {},
) {
    YappBackground(
        color = YappTheme.colorScheme.backgroundNormalAlternative
    ) {
        val scrollState = rememberScrollState()
        Column {
            YappDefaultHeader(
                onClickRightIcon = { onIntent(HomeIntent.ClickSettingButton) }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                if (homeState.isLoading) {
                    ProfileLoadingSection(homeState.name)
                    Spacer(Modifier.height(8.dp))
                    NoticeSection(
                        null,
                        onMoreButtonClick = { onIntent(HomeIntent.ClickMoreButton) },
                        onNoticeDetailClick = { onIntent(HomeIntent.ClickNoticeItem(it)) }
                    )
                } else {
                    ProfileSection(
                        name = homeState.name,
                        activityStatus = homeState.role,
                        generation = (homeState.activityUnits.firstOrNull()?.generation) ?: 25,
                        position = (homeState.activityUnits.firstOrNull()?.position) ?: "Android"
                    )
                    Spacer(Modifier.height(8.dp))
                    NoticeSection(
                        noticeInfo = hmeState.noticeInfo,
                        onMoreButtonClick = { onIntent(HomeIntent.ClickMoreButton) },
                        onNoticeDetailClick = { onIntent(HomeIntent.ClickNoticeItem(it)) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    YappTheme {
        HomeRoute(
            navigateToLogin = {},
            navigateToNotice = {},
            navigateToSetting = {},
            navigateToNoticeDetail = {}
        )
    }
}