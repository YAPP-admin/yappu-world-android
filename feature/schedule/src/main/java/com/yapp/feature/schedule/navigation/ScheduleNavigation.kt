package com.yapp.feature.schedule.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yapp.feature.schedule.ScheduleRoute
import kotlinx.serialization.Serializable

@Serializable
data object ScheduleRoute

fun NavController.navigateToSchedule(navOptions: NavOptions? = null) {
    navigate(ScheduleRoute, navOptions)
}

fun NavGraphBuilder.scheduleNavGraph(
    handleException: (Throwable) -> Unit,
    navigateToLogin: () -> Unit,
) {
    composable<ScheduleRoute> {
        ScheduleRoute(
            handleException = handleException,
            navigateToLogin = navigateToLogin,
        )
    }
}