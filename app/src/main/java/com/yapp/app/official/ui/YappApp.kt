package com.yapp.app.official.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
            AnimatedVisibility(
                navigator.shouldShowBottomBar,
                enter = slideInVertically { it },
                exit = slideOutVertically { it },
            ) {
                val destinations = TopLevelDestination.entries
                YappBottomNavigationBar(
                    destinations = destinations,
                    currentDestination = navigator.currentDestination,
                    onNavigateToDestination = { destination ->
                        navigator.navigateToTopLevelDestination(destination)
                    }
                )
            }
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
            val selected = currentDestination.isRouteInHierarchy(destination.baseRoute)

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