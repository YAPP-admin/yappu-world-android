package com.yapp.feature.notice.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yapp.feature.NoticeDetail.NoticeDetaildetail.NoticeDetailRoute
import com.yapp.feature.notice.notice.NoticeRoute
import com.yapp.feature.notice.notice.NoticeScreen
import com.yapp.feature.notice.notice.NoticeRoute
import kotlinx.serialization.Serializable

@Serializable
data object NoticeRoute

@Serializable
data class NoticeDetailRoute(
    val noticeId: String,
)

fun NavController.navigateToNotice(navOptions: NavOptions? = null) {
    navigate(NoticeRoute, navOptions)
}

fun NavController.navigateToNoticeDetail(noticeId: String, navOptions: NavOptions? = null) {
    navigate(NoticeDetailRoute(noticeId = noticeId), navOptions)
}

fun NavGraphBuilder.noticeNavGraph(
    navigateToNoticeDetail: (String) -> Unit,
    navigateBack: () -> Unit,
    ) {
    composable<NoticeRoute> {
        NoticeRoute(
            navigateToNoticeDetail = { noticeId ->
                navigateToNoticeDetail(noticeId)
            },
            navigateBack = navigateBack
        )
    }
}

