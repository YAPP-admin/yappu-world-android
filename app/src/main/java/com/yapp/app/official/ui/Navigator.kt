package com.yapp.app.official.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.yapp.app.official.navigation.TopLevelDestination
import com.yapp.feature.home.navigation.navigateToHome
import com.yapp.feature.home.navigation.navigateToSetting
import com.yapp.feature.login.navigation.LoginRoute
import com.yapp.feature.login.navigation.navigateToLogin
import com.yapp.feature.notice.navigation.navigateToNotice
import com.yapp.feature.notice.navigation.navigateToNoticeDetail
import com.yapp.feature.profile.navigation.navigateToProfile
import com.yapp.feature.schedule.navigation.navigateToSchedule
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
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val shouldShowBottomBar: Boolean
        @Composable get() {
            val topLevelRoutes = TopLevelDestination.entries.map { it.route.qualifiedName }
            return currentDestination?.route in topLevelRoutes
        }

    var startDestination: Any by mutableStateOf(LoginRoute)

    fun navigateLoginScreen(navOptions: NavOptions? = null) {
        navController.navigateToLogin(navOptions)
    }

    fun navigateSignUpScreen(step : String) {
        navController.navigateToSignUp(step)
    }

    fun navigateHomeScreen(navOptions: NavOptions? = null) {
        navController.navigateToHome(navOptions = navOptions)
    }

    fun navigateScheduleScreen(navOptions: NavOptions? = null) {
        navController.navigateToSchedule(navOptions = navOptions)
    }

    fun navigateProfileScreen(navOptions: NavOptions? = null) {
        navController.navigateToProfile(navOptions = navOptions)
    }

    fun navigateSettingScreen(){
        navController.navigateToSetting()
    }

    fun navigateNoticeScreen() {
        navController.navigateToNotice()
    }

    fun navigateToNoticeDetail(noticeId: String) {
        navController.navigateToNoticeDetail(noticeId)
    }

    fun popBackStack() {
        navController.popBackStack()
    }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.HOME -> {
                navController.navigateToHome(navOptions = topLevelNavOptions)
            }
            TopLevelDestination.SCHEDULE -> {
                navController.navigateToSchedule(navOptions = topLevelNavOptions)
            }
            TopLevelDestination.BOARD -> {
                navController.navigateToNotice(navOptions = topLevelNavOptions)
            }
            TopLevelDestination.PROFILE -> {
                navController.navigateToProfile(navOptions = topLevelNavOptions)
            }
        }
    }
}

val clearBackStackNavOptions = NavOptions.Builder().setPopUpTo(0, true).build()