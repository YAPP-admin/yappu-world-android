package com.yapp.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yapp.feature.login.LoginRoute
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    navigate(LoginRoute, navOptions)
}

fun NavGraphBuilder.loginNavGraph(
    onNavigateToSignUpName: () -> Unit,
    onNavigateToSignUpPending: () -> Unit,
    onNavigateToSignUpReject: () -> Unit,
    onNavigateToHome: () -> Unit,
    onHandleException: (Throwable) -> Unit,
) {
    composable<LoginRoute> {
        LoginRoute(
            navigateToSignupName = onNavigateToSignUpName,
            navigateToSignupPending = onNavigateToSignUpPending,
            navigateToSignupReject = onNavigateToSignUpReject,
            navigateToHome = onNavigateToHome,
            handleException = onHandleException,
        )
    }
}
