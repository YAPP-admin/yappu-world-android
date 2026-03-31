package com.yapp.feature.session.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yapp.feature.session.SessionRoute
import kotlinx.serialization.Serializable

@Serializable
data class SessionRoute(val sessionId: String)

fun NavController.navigateToSession(sessionId: String, navOptions: NavOptions? = null) {
    navigate(SessionRoute(sessionId), navOptions)
}

fun NavGraphBuilder.sessionNavGraph(
    onNavigateBack: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onNavigateToNoticeDetail: (String) -> Unit = {},
    onHandleException: (Throwable) -> Unit = {},
) {
    composable<SessionRoute> {
        SessionRoute(
            navigateToBack = onNavigateBack,
            navigateToLogin = onNavigateToLogin,
            navigateToNoticeDetail = onNavigateToNoticeDetail,
            handleException = onHandleException,
        )
    }
}

