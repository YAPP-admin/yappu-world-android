package com.yapp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yapp.feature.signup.signup.SignUpRoute
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRoute(val currentStep : String)

fun NavController.navigateToSignUp(step : String, navOptions: NavOptions? = null) {
    navigate(SignUpRoute(step), navOptions)
}

fun NavGraphBuilder.signupNavGraph(
    navigateBack: () -> Unit,
    navigateHome: () -> Unit,
) {
    composable<SignUpRoute> {
        SignUpRoute(
            navigateBack = navigateBack,
            navigateHome = navigateHome,
        )
    }
}
