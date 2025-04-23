package com.yapp.app.official.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.yapp.app.official.ui.NavigatorState
import com.yapp.app.official.ui.clearBackStackNavOptions
import com.yapp.feature.home.navigation.homeNavGraph
import com.yapp.feature.home.navigation.settingNavGraph
import com.yapp.feature.login.navigation.loginNavGraph
import com.yapp.feature.notice.navigation.noticeDetailNavGraph
import com.yapp.feature.notice.navigation.noticeNavGraph
import com.yapp.feature.signup.navigation.signupNavGraph
import com.yapp.feature.signup.signup.SignUpStep

@Composable
fun YappNavHost(
    navigator: NavigatorState,
    modifier: Modifier = Modifier,
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
            navigateSignUpPending = {navigator.navigateSignUpScreen(SignUpStep.Pending.name)},
            navigateSignUpReject = {navigator.navigateSignUpScreen(SignUpStep.Reject.name)},
            navigateHome = {
                navigator.navigateHomeScreen(
                    navOptions = clearBackStackNavOptions
                )
            }
        )
        signupNavGraph(
            navigateBack = { navigator.popBackStack() },
            navigateHome = {
                navigator.navigateHomeScreen(
                    navOptions = clearBackStackNavOptions
                )
            }
        )
        homeNavGraph(
            navigateNotice = { navigator.navigateNoticeScreen() },
            navigateSetting = { navigator.navigateSettingScreen() },
            navigateLogin = {navigator.navigateLoginScreen(
                navOptions = clearBackStackNavOptions
            )},
            navigateToNoticeDetail = { noticeId ->
                navigator.navigateToNoticeDetail(noticeId)
            }
        )
        settingNavGraph(
            navigateLogin = {
                navigator.navigateLoginScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            navigateBack = { navigator.popBackStack() }
        )
        noticeNavGraph(
            navigateToNoticeDetail = { noticeId ->
                navigator.navigateToNoticeDetail(noticeId)
            },
            navigateBack = {navigator.popBackStack()}
        )
        noticeDetailNavGraph(
            navigateBack = {navigator.popBackStack()}
        )
    }
}