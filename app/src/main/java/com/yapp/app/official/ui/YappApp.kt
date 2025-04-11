package com.yapp.app.official.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.yapp.app.official.navigation.TopLevelDestination
import com.yapp.app.official.navigation.YappNavHost
import com.yapp.core.ui.component.BottomNavigationBar
import com.yapp.core.ui.component.BottomNavigationBarItem
import kotlin.reflect.KClass


@Composable
fun YappApp(navigator: NavigatorState) {
    Scaffold(
        bottomBar = {
            val destinations = TopLevelDestination.entries
            val currentDestination = navigator.navController.currentDestination
            YappBottomNavigationBar(
                destinations = destinations,
                currentDestination = currentDestination,
                onNavigateToDestination = { destination ->
                    navigator.navigateToTopLevelDestination(destination)
                }
            )
        },
    ) { padding ->
        YappNavHost(navigator, modifier = Modifier.padding(padding))
    }
}

@Composable
private fun YappBottomNavigationBar(
    modifier: Modifier = Modifier,
    destinations: List<TopLevelDestination>,
    currentDestination: NavDestination?,
    onNavigateToDestination: (TopLevelDestination) -> Unit
) {
    BottomNavigationBar(
        modifier = modifier
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isRouteInHierarchy(destination.route)

            BottomNavigationBarItem(
                selected = selected,
                iconTextId = destination.iconTextId,
                selectedIcon = destination.selectedIcon,
                unselectedIcon = destination.unselectedIcon,
                onClick = {
                    if (!selected) {
                        onNavigateToDestination(destination)
                    }
                },
            )
        }
    }
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false

private fun NavDestination?.isCurrentRoute(route: KClass<*>): Boolean {
    return this?.route == route.simpleName
}