package com.yapp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yapp.feature.signup.signup.SignUpRoute
import kotlinx.serialization.Serializable

@Serializable data object SignUpRoute

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) { navigate(SignUpRoute, navOptions) }

fun NavGraphBuilder.signupNavGraph(){
    composable<SignUpRoute> {
        SignUpRoute()
    }
}
