package com.yapp.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.header.YappDefaultHeader
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.home.component.NoticeSection
import com.yapp.feature.home.component.ProfileSection

@Composable
internal fun HomeRoute(
    navigateToNotice: () -> Unit,
    navigateToSetting: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    viewModel.store.sideEffects.collectWithLifecycle { effect ->
        when (effect) {
            HomeSideEffect.NavigateToNotice -> navigateToNotice()
            HomeSideEffect.NavigateToSetting -> navigateToSetting()
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
                ProfileSection(
                    homeState.name,
                    "활동회원",
                    (homeState.activityUnits[0].generation) ?: 25,
                    (homeState.activityUnits[0].position) ?: "Android"
                )
                Spacer(Modifier.height(8.dp))
                NoticeSection(
                    noticeInfo = homeState.noticeInfo,
                    onMoreButtonClick = { onIntent(HomeIntent.ClickMoreButton)}
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    YappTheme {
        HomeRoute({}, {})
    }
}