package com.yapp.feature.history.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yapp.feature.history.attend.AttendHistoryRoute
import com.yapp.feature.history.previous.PreviousHistoryRoute
import kotlinx.serialization.Serializable

@Serializable
data object AttendanceHistory

@Serializable
data object PreviousHistory

fun NavController.navigateToAttendance() {
    navigate(AttendanceHistory)
}

fun NavController.navigateToPreviousHistory() {
    navigate(PreviousHistory)
}

fun NavGraphBuilder.attendanceHistoryNavGraph(
    onNavigateBack: () -> Unit,
) {
    composable<AttendanceHistory> {
        AttendHistoryRoute(navigateToBack = onNavigateBack)
    }
}

fun NavGraphBuilder.previousHistoryNavGraph(
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onHandleException: (Throwable) -> Unit,
) {
    composable<PreviousHistory> {
        PreviousHistoryRoute(
            navigateToBack = onNavigateBack,
            navigateToLogin = onNavigateToLogin,
            handleException = onHandleException,
        )
    }
}
