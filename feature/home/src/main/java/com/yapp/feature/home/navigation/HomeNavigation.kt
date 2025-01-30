package com.yapp.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yapp.feature.home.HomeScreen
import com.yapp.feature.home.setting.SettingRoute
import kotlinx.serialization.Serializable

@Serializable data object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) { navigate(HomeRoute, navOptions) }

fun NavGraphBuilder.homeNavGraph(){
    composable<HomeRoute> {
        SettingRoute()
    }
}
