package com.yapp.feature.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yapp.feature.profile.ProfileRoute
import kotlinx.serialization.Serializable

@Serializable
data object ProfileRoute

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    navigate(ProfileRoute, navOptions)
}

fun NavGraphBuilder.profileNavGraph(
    onNavigateToSetting: () -> Unit,
    onNavigateToAttendHistory: () -> Unit,
    onNavigateToPreviousHistory: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    composable<ProfileRoute> {
        ProfileRoute(
            onNavigateToSettings = onNavigateToSetting,
            onNavigateToAttendHistory = onNavigateToAttendHistory,
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToPreviousHistory = onNavigateToPreviousHistory
        )
    }
}
