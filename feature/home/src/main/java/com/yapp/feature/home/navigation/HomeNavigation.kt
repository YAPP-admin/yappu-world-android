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
    onNavigateToLogin: () -> Unit,
    onNavigateToSchedule: () -> Unit,
    onNavigateToAttendanceHistory: () -> Unit,
    onNavigateToNotice: (id: String) -> Unit,
    onNavigateToSessionDetail: (String) -> Unit,
    onHandleException: (Throwable) -> Unit,
) {
    composable<HomeRoute> {
        HomeRoute(
            navigateToLogin = onNavigateToLogin,
            navigateToSchedule = onNavigateToSchedule,
            navigateToAttendanceHistory = onNavigateToAttendanceHistory,
            navigateToNotice = onNavigateToNotice,
            navigateToSessionDetail = onNavigateToSessionDetail,
            handleException = onHandleException,
        )
    }
}


