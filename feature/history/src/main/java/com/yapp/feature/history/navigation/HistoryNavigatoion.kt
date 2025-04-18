package com.yapp.feature.history.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yapp.feature.history.AttendHistoryRoute
import kotlinx.serialization.Serializable

@Serializable
data object AttendanceHistory

fun NavController.navigateToAttendance() {
    navigate(AttendanceHistory)
}

fun NavGraphBuilder.attendanceHistoryNavGraph(
    navigateToBack: () -> Unit
) {
    composable<AttendanceHistory> {
        AttendHistoryRoute(navigateToBack = navigateToBack)
    }
}
