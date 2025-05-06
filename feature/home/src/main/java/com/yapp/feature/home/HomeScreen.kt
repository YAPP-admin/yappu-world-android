package com.yapp.feature.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.yapp.feature.home.component.HomeAttendanceContents
import com.yapp.feature.home.component.HomeAttendanceNotice
import com.yapp.feature.home.component.HomeHeader
import com.yapp.feature.home.dialog.AttendanceDialog

@Composable
internal fun HomeRoute(
    navigateToLogin: () -> Unit,
    navigateToSchedule: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.store.onIntent(HomeIntent.EnterHomeScreen)
    }

    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    viewModel.store.sideEffects.collectWithLifecycle { effect ->
        when (effect) {
            HomeSideEffect.NavigateToSchedule -> navigateToSchedule()
            is HomeSideEffect.ShowToast -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            HomeSideEffect.NavigateToLogin -> navigateToLogin()
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
    val colorSteps = arrayOf(
        0.2f to YappTheme.colorScheme.primaryNormal,
        1f to YappTheme.colorScheme.secondaryNormal
    )

    YappBackground(
        color = YappTheme.colorScheme.staticWhite,
        contentWindowInsets = WindowInsets.navigationBars,
    ) {
        Column {
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

            HomeAttendanceContents(
                upcomingSession = homeState.upcomingSession,
                onClickAttend = { onIntent(HomeIntent.ClickRequestAttendCode) }
            )
        }
    }

    if (homeState.showAttendCodeBottomSheet) {
        AttendanceDialog(
            onDismissRequest = {
                onIntent(HomeIntent.ClickDismissDialog)
            },
            clickAttendanceButton = { code ->
                onIntent(
                    HomeIntent.ClickRequestAttendance(
                        sessionId = homeState.upcomingSession?.sessionId.orEmpty(),
                        code = code
                    )
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    YappTheme {
        HomeRoute(
            navigateToLogin = {},
            navigateToSchedule = {}
        )
    }
}