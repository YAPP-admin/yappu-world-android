package com.yapp.feature.session.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yapp.feature.session.SessionRoute
import kotlinx.serialization.Serializable

@Serializable
data object SessionRoute

fun NavController.navigateToSession(navOptions: NavOptions? = null) {
    navigate(SessionRoute, navOptions)
}

fun NavGraphBuilder.sessionNavGraph() {
    composable<SessionRoute> {
        SessionRoute()
    }
}

