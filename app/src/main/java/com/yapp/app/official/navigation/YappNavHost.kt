package com.yapp.app.official.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.yapp.app.official.ui.NavigatorState
import com.yapp.app.official.ui.clearBackStackNavOptions
import com.yapp.feature.history.navigation.attendanceHistoryNavGraph
import com.yapp.feature.history.navigation.previousHistoryNavGraph
import com.yapp.feature.home.navigation.homeNavGraph
import com.yapp.feature.login.navigation.loginNavGraph
import com.yapp.feature.notice.navigation.noticeDetailNavGraph
import com.yapp.feature.notice.navigation.noticeNavGraph
import com.yapp.feature.profile.navigation.profileNavGraph
import com.yapp.feature.schedule.navigation.scheduleNavGraph
import com.yapp.feature.setting.navigation.settingNavGraph
import com.yapp.feature.signup.navigation.signupNavGraph
import com.yapp.feature.signup.signup.SignUpStep

@Composable
fun YappNavHost(
    navigator: NavigatorState,
    modifier: Modifier = Modifier,
    handleException: (Throwable) -> Unit,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        loginNavGraph(
            navigateSignUpName = { navigator.navigateSignUpScreen(SignUpStep.Name.name) },
            navigateSignUpPending = { navigator.navigateSignUpScreen(SignUpStep.Pending.name) },
            navigateSignUpReject = { navigator.navigateSignUpScreen(SignUpStep.Reject.name) },
            navigateHome = {
                navigator.navigateHomeScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            handleException = handleException,
        )
        signupNavGraph(
            navigateBack = { navigator.popBackStack() },
            navigateHome = {
                navigator.navigateHomeScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            handleException = handleException,
        )
        homeNavGraph(
            navigateLogin = {
                navigator.navigateLoginScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            navigateSchedule = {
                navigator.navigateToTopLevelDestination(TopLevelDestination.SCHEDULE)
            },
            handleException = handleException,
        )
        settingNavGraph(
            navigateLogin = {
                navigator.navigateLoginScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            navigateBack = { navigator.popBackStack() },
            handleException = handleException,
        )
        scheduleNavGraph(
            navigateToLogin = {
                navigator.navigateLoginScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            handleException = handleException,
        )
        noticeNavGraph(
            navigateToNoticeDetail = { noticeId ->
                navigator.navigateToNoticeDetail(noticeId)
            },
            navigateToLogin = {
                navigator.navigateLoginScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            handleException = handleException,
        )
        noticeDetailNavGraph(
            navigateBack = { navigator.popBackStack() },
            navigateLogin = {
                navigator.navigateLoginScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            handleException = handleException,
        )
        profileNavGraph(
            onNavigateToSetting = { navigator.navigateSettingScreen() },
            onNavigateToLogin = { navigator.navigateLoginScreen(clearBackStackNavOptions) },
            onNavigateToPreviousHistory = { navigator.navigateToPreviousHistory() },
            onNavigateToAttendHistory = { navigator.navigateToAttendance() },
            handleException = handleException,
        )
        attendanceHistoryNavGraph(
            navigateToBack = { navigator.popBackStack() }
        )
        previousHistoryNavGraph(
            navigateToBack = { navigator.popBackStack() },
            navigateToLogin = { navigator.navigateLoginScreen(clearBackStackNavOptions) },
            handleException = handleException,
        )
    }
}