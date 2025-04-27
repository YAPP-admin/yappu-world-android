package com.yapp.feature.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.home.component.HomeStickHeader

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    homeState: HomeState,
    onIntent: (HomeIntent) -> Unit = {},
) {
    val colorStops = arrayOf(
        0.2f to YappTheme.colorScheme.primaryNormal,
        1f to YappTheme.colorScheme.secondaryNormal
    )

    YappBackground(
        color = YappTheme.colorScheme.backgroundNormalAlternative
    ) {
        LazyColumn(userScrollEnabled = false) {
            stickyHeader {
                HomeStickHeader(
                    modifier = Modifier
                        .background(brush = Brush.horizontalGradient(colorStops = colorStops))
                        .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
                    sessions = homeState.sessions
                )
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