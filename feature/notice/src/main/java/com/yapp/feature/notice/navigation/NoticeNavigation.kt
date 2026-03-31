package com.yapp.feature.notice.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.yapp.feature.notice.notice.NoticeRoute
import com.yapp.feature.notice.noticedetail.NoticeDetailRoute
import kotlinx.serialization.Serializable

@Serializable
data object NoticeRoute

@Serializable
data class NoticeDetailRoute(
    val id: String,
)

fun NavController.navigateToNotice(navOptions: NavOptions? = null) {
    navigate(NoticeRoute, navOptions)
}

fun NavController.navigateToNoticeDetail(noticeId: String, navOptions: NavOptions? = null) {
    navigate(route = NoticeDetailRoute(id = noticeId), navOptions)
}

fun NavGraphBuilder.noticeNavGraph(
    onNavigateToNoticeDetail: (String) -> Unit,
    onHandleException: (Throwable) -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    composable<NoticeRoute> {
        NoticeRoute(
            navigateToNoticeDetail = { noticeId ->
                onNavigateToNoticeDetail(noticeId)
            },
            handleException = onHandleException,
            navigateToLogin = onNavigateToLogin,
        )
    }
}

fun NavGraphBuilder.noticeDetailNavGraph(
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onHandleException: (Throwable) -> Unit,
) {
    composable<NoticeDetailRoute>(
        deepLinks = listOf(
            navDeepLink<NoticeDetailRoute>(
                basePath = "https://yapp.co.kr/notices"
            )
        )
    ) { backStackEntry ->
        val noticeId = backStackEntry.toRoute<NoticeDetailRoute>().id
        NoticeDetailRoute(
            noticeId = noticeId,
            navigateBack = onNavigateBack,
            navigateLogin = onNavigateToLogin,
            handleException = onHandleException,
        )
    }
}

