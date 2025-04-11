package com.yapp.feature.schedule.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ScheduleRoute

fun NavController.navigateToSchedule(navOptions: NavOptions? = null) {
    navigate(ScheduleRoute, navOptions)
}

fun NavGraphBuilder.scheduleNavGraph() {
    composable<ScheduleRoute> {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Schedule Screen")
        }
    }
}