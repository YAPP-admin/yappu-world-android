package com.yapp.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yapp.feature.home.HomeRoute
import com.yapp.feature.home.setting.SettingRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data object SettingRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(HomeRoute, navOptions)
}

fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    navigate(SettingRoute, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    navigateNotice: () -> Unit,
    navigateSetting: () -> Unit
) {
    composable<HomeRoute> {
        HomeRoute(
            navigateToNotice = navigateNotice,
            navigateToSetting = navigateSetting,
        )
    }
}

fun NavGraphBuilder.settingNavGraph() {
    composable<SettingRoute> {
        SettingRoute()
    }
}
