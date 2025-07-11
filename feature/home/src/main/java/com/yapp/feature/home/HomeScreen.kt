package com.yapp.feature.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.home.component.HomeAttendanceContent
import com.yapp.feature.home.component.HomeAttendanceNotice
import com.yapp.feature.home.component.HomeHeader
import com.yapp.feature.home.component.HomeRecentAttendanceHistory
import com.yapp.feature.home.dialog.AttendanceDialog

@Composable
internal fun HomeRoute(
    navigateToLogin: () -> Unit,
    navigateToSchedule: () -> Unit,
    navigateToAttendanceHistory: () -> Unit,
    handleException: (Throwable) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.store.onIntent(HomeIntent.EnterHomeScreen)
    }

    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    viewModel.store.sideEffects.collectWithLifecycle { effect ->
        when (effect) {
            HomeSideEffect.NavigateToLogin -> navigateToLogin()
            HomeSideEffect.NavigateToSchedule -> navigateToSchedule()
            HomeSideEffect.NavigateToAttendanceHistory -> navigateToAttendanceHistory()

            is HomeSideEffect.ShowToast -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            is HomeSideEffect.HandleException -> handleException(effect.exception)
        }
    }

    HomeScreen(
        homeState = uiState,
        onIntent = { viewModel.store.onIntent(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeState: HomeState,
    onIntent: (HomeIntent) -> Unit = {},
) {
    val colorSteps = arrayOf(
        0.2f to YappTheme.colorScheme.primaryNormal,
        1f to YappTheme.colorScheme.secondaryNormal
    )

    val pullToRefreshState = rememberPullToRefreshState()

    YappBackground(
        color = YappTheme.colorScheme.staticWhite,
        contentWindowInsets = WindowInsets.navigationBars,
    ) {
        PullToRefreshBox(
            isRefreshing = homeState.isLoading,
            state = pullToRefreshState,
            onRefresh = {
                onIntent(HomeIntent.Refresh)
            },
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = homeState.isLoading,
                    state = pullToRefreshState,
                    containerColor = YappTheme.colorScheme.staticWhite
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                HomeHeader(
                    modifier = Modifier
                        .background(brush = Brush.horizontalGradient(colorStops = colorSteps))
                        .padding(WindowInsets.statusBars.asPaddingValues())
                        .padding(top = 18.dp),
                    sessions = homeState.sessionList.sessions,
                    upcomingSessionId = homeState.sessionList.upcomingSessionId,
                    onClickShowAll = { onIntent(HomeIntent.ClickShowAllSession) },
                )

                HomeAttendanceNotice(
                    upcomingSession = homeState.upcomingSession
                )

                HomeAttendanceContent(
                    upcomingSession = homeState.upcomingSession,
                    onClickAttend = { onIntent(HomeIntent.ClickRequestAttendCode) }
                )

                HomeRecentAttendanceHistory(
                    recentAttendanceHistory = homeState.recentAttendanceHistory,
                    onClickShowAll = { onIntent(HomeIntent.ClickShowAllAttendanceHistory) }
                )
            }
        }
    }

    if (homeState.showAttendCodeBottomSheet) {
        AttendanceDialog(
            code = homeState.attendanceCodeDigits,
            inputCompleteButtonEnabled = homeState.inputCompleteButtonEnabled,
            isCodeInputTextError = homeState.showAttendanceCodeError,
            onDismissRequest = {
                onIntent(HomeIntent.ClickDismissDialog)
            },
            onCodeChange = { code ->
                onIntent(
                    HomeIntent.ChangeAttendanceCodeDigits(code)
                )
            },
            clickAttendanceButton = {
                onIntent(
                    HomeIntent.ClickRequestAttendance
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    YappTheme {
        HomeScreen(
            homeState = HomeState()
        )
    }
}