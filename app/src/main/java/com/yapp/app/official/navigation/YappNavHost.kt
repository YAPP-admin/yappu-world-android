package com.yapp.app.official.navigation

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
import com.yapp.feature.session.navigation.sessionNavGraph
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
        enterTransition = { yappEnterTransition() },
        exitTransition = { yappExitTransition() },
        popEnterTransition = { yappPopEnterTransition() },
        popExitTransition = { yappPopExitTransition() },
    ) {
        loginNavGraph(
            onNavigateToSignUpName = { navigator.navigateSignUpScreen(SignUpStep.Name.name) },
            onNavigateToSignUpPending = { navigator.navigateSignUpScreen(SignUpStep.Pending.name) },
            onNavigateToSignUpReject = { navigator.navigateSignUpScreen(SignUpStep.Reject.name) },
            onNavigateToHome = {
                navigator.navigateHomeScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            onHandleException = handleException,
        )
        signupNavGraph(
            onNavigateBack = { navigator.popBackStack() },
            onNavigateToHome = {
                navigator.navigateHomeScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            onHandleException = handleException,
        )
        homeNavGraph(
            onNavigateToLogin = {
                navigator.navigateLoginScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            onNavigateToSchedule = {
                navigator.navigateToTopLevelDestination(TopLevelDestination.SCHEDULE)
            },
            onNavigateToNotice = {
                navigator.navigateNoticeDetail(it)
            },
            onNavigateToSessionDetail = { sessionId ->
                navigator.navigateSessionScreen(sessionId)
            },
            onNavigateToAttendanceHistory = {
                navigator.navigateAttendance()
            },
            onHandleException = handleException,
        )
        settingNavGraph(
            onNavigateToLogin = {
                navigator.navigateLoginScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            onNavigateBack = { navigator.popBackStack() },
            onHandleException = handleException,
        )
        scheduleNavGraph(
            onNavigateToLogin = {
                navigator.navigateLoginScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            onHandleException = handleException,
            onNavigateToSessionDetail = { sessionId ->
                navigator.navigateSessionScreen(sessionId)
            },
        )
        noticeNavGraph(
            onNavigateToNoticeDetail = { noticeId ->
                navigator.navigateNoticeDetail(noticeId)
            },
            onNavigateToLogin = {
                navigator.navigateLoginScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            onHandleException = handleException,
        )
        noticeDetailNavGraph(
            onNavigateBack = { navigator.popBackStack() },
            onNavigateToLogin = {
                navigator.navigateLoginScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            onHandleException = handleException,
        )
        profileNavGraph(
            onNavigateToSetting = { navigator.navigateSettingScreen() },
            onNavigateToLogin = { navigator.navigateLoginScreen(clearBackStackNavOptions) },
            onNavigateToPreviousHistory = { navigator.navigatePreviousHistory() },
            onNavigateToAttendHistory = { navigator.navigateAttendance() },
            onHandleException = handleException,
        )
        attendanceHistoryNavGraph(
            onNavigateBack = { navigator.popBackStack() },
        )
        previousHistoryNavGraph(
            onNavigateBack = { navigator.popBackStack() },
            onNavigateToLogin = { navigator.navigateLoginScreen(clearBackStackNavOptions) },
            onHandleException = handleException,
        )
        sessionNavGraph(
            onNavigateBack = { navigator.popBackStack() },
            onNavigateToLogin = {
                navigator.navigateLoginScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            onNavigateToNoticeDetail = { noticeId ->
                navigator.navigateNoticeDetail(noticeId)
            },
            onHandleException = handleException,
        )
    }
}