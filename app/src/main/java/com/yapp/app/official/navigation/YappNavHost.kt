package com.yapp.app.official.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.yapp.app.official.ui.NavigatorState
import com.yapp.feature.home.navigation.homeNavGraph
import com.yapp.feature.login.navigation.loginNavGraph
import com.yapp.feature.notice.navigation.noticeNavGraph
import com.yapp.feature.signup.navigation.signupNavGraph


@Composable
fun YappNavHost(
    navigator: NavigatorState,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        modifier = modifier,
    ) {
        loginNavGraph(
            signupDestination = { navigator.navigateSignUpScreen() }
        )
        signupNavGraph()
        homeNavGraph()
        noticeNavGraph()
    }
}