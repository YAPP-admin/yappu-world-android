package com.yapp.app.official.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.yapp.app.official.ui.NavigatorState
import com.yapp.app.official.ui.clearBackStackNavOptions
import com.yapp.feature.home.navigation.homeNavGraph
import com.yapp.feature.home.navigation.settingNavGraph
import com.yapp.feature.login.navigation.loginNavGraph
import com.yapp.feature.notice.navigation.noticeNavGraph
import com.yapp.feature.signup.navigation.signupNavGraph

@Composable
fun YappNavHost(
    navigator: NavigatorState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        modifier = modifier,
    ) {
        loginNavGraph(
            navigateSignUp = { navigator.navigateSignUpScreen() },
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
            navigateSetting = { navigator.navigateSettingScreen() }
        )
        settingNavGraph(
            navigateLogin = {
                navigator.navigateLoginScreen(
                    navOptions = clearBackStackNavOptions
                )
            },
            navigateBack = { navigator.popBackStack() }
        )
        noticeNavGraph()
    }
}