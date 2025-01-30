package com.yapp.app.official.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yapp.feature.home.navigation.HomeRoute
import com.yapp.feature.home.navigation.navigateToHome
import com.yapp.feature.login.navigation.LoginRoute
import com.yapp.feature.login.navigation.navigateToLogin
import com.yapp.feature.notice.navigation.navigateToNotice
import com.yapp.feature.signup.navigation.SignUpRoute
import com.yapp.feature.signup.navigation.navigateToSignUp


@Composable
fun rememberNavigator(
    navController: NavHostController = rememberNavController(),
): NavigatorState = remember(navController) {
    NavigatorState(
        navController = navController,
    )
}


class NavigatorState(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = SignUpRoute

    fun navigateLoginScreen() {
        navController.navigateToLogin()
    }

    fun navigateSignUpScreen() {
        navController.navigateToSignUp()
    }

    fun navigateHomeScreen() {
        navController.navigateToHome()
    }

    fun navigateNoticeScreen() {
        navController.navigateToNotice()
    }

    fun popBackStack() {
        navController.popBackStack()
    }
}
