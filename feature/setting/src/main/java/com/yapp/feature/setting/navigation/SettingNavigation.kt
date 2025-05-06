package com.yapp.feature.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SettingRoute

fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    navigate(SettingRoute, navOptions)
}

fun NavGraphBuilder.settingNavGraph(
    navigateLogin: () -> Unit,
    navigateBack: () -> Unit,
    handleException: (Throwable) -> Unit,
) {
    composable<SettingRoute> {
        com.yapp.feature.setting.SettingRoute(
            navigateBack = navigateBack,
            navigateLogin = navigateLogin,
            handleException = handleException,
        )
    }
}