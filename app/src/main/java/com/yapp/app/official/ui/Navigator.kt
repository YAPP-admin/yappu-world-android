package com.yapp.app.official.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yapp.featrue.profile.navigation.navigateToProfile
import com.yapp.feature.home.navigation.navigateToHome
import com.yapp.feature.home.navigation.navigateToSetting
import com.yapp.feature.login.navigation.LoginRoute
import com.yapp.feature.login.navigation.navigateToLogin
import com.yapp.feature.notice.navigation.navigateToNotice
import com.yapp.feature.notice.navigation.navigateToNoticeDetail
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
}

val clearBackStackNavOptions = NavOptions.Builder().setPopUpTo(0, true).build()
