package com.yapp.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yapp.feature.login.LoginRoute
import kotlinx.serialization.Serializable

@Serializable data object LoginRoute

fun NavController.navigateToLogin(navOptions: NavOptions? = null) { navigate(LoginRoute, navOptions) }

fun NavGraphBuilder.loginNavGraph(
    navigateSignUp: (String) -> Unit,
    navigateHome: () -> Unit,
    ){
    composable<LoginRoute> {
        LoginRoute(navigateSignUp,navigateHome)
    }
}
