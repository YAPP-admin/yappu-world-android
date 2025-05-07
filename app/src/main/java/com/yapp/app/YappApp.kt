package com.yapp.app

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.yapp.app.official.YappAppViewModel
import com.yapp.app.official.navigation.TopLevelDestination
import com.yapp.app.official.navigation.YappNavHost
import com.yapp.app.official.ui.NavigatorState
import com.yapp.core.designsystem.component.alert.YappAlertLongDialog
import com.yapp.core.ui.R
import com.yapp.core.ui.component.BottomNavigationBar
import com.yapp.core.ui.component.BottomNavigationBarItem
import com.yapp.core.ui.extension.safeOpenUri
import kotlin.reflect.KClass


@Composable
fun YappApp(
    navigator: NavigatorState,
    viewModel: YappAppViewModel = hiltViewModel()
) {

    var showCommonErrorDialog by remember {
        mutableStateOf(false)
    }

    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = navigator.shouldShowBottomBar,
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
        contentWindowInsets = WindowInsets(0.dp),
    ) { padding ->
        YappNavHost(
            navigator = navigator,
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(
                    WindowInsets(0.dp).takeIf { !navigator.shouldShowBottomBar } ?: WindowInsets.navigationBars
                ),
            handleException = { showCommonErrorDialog = true }
        )
    }

    if (showCommonErrorDialog) {
        CommonErrorDialog(
            onDismissRequest = { showCommonErrorDialog = false },
            onActionButtonClick = { showCommonErrorDialog = false },
            onRecommendActionButtonClick = {
                viewModel.onCommonErrorDialogRecommendActionButtonClick(
                    onSuccess = {
                        uriHandler.safeOpenUri(it)
                    },
                    onError = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_message_error_loading_url),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        )
    }
}

@Composable
private fun CommonErrorDialog(
    onDismissRequest: () -> Unit,
    onActionButtonClick: () -> Unit,
    onRecommendActionButtonClick: () -> Unit,
) {
    YappAlertLongDialog(
        onDismissRequest = onDismissRequest,
        title = "앗! 예기치 못한 오류가 발생했어요.",
        body = "재시도 후에도 같은 문제가 발생한다면, \n" +
                "개발팀에 제보해주세요! \n" +
                "서비스 품질 향상에 큰 도움이 됩니다 :) ",
        recommendActionButtonText = "제보하러 가기",
        actionButtonText = "닫기",
        onRecommendActionButtonClick = onRecommendActionButtonClick,
        onActionButtonClick = onActionButtonClick,
    )
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