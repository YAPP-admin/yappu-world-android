package com.yapp.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yapp.feature.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(HomeRoute, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    navigateLogin : () -> Unit,
    navigateSchedule: () -> Unit,
    navigateAttendanceHistory: () -> Unit,
    handleException: (Throwable) -> Unit,
) {
    composable<HomeRoute> {
        HomeRoute(
            navigateToLogin = navigateLogin,
            navigateToSchedule = navigateSchedule,
            navigateToAttendanceHistory = navigateAttendanceHistory,
            handleException = handleException,
        )
    }
}


