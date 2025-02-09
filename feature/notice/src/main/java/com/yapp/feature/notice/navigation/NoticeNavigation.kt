package com.yapp.feature.notice.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yapp.feature.notice.notice.NoticeScreen
import kotlinx.serialization.Serializable

@Serializable data object NoticeRoute

fun NavController.navigateToNotice(navOptions: NavOptions? = null) { navigate(NoticeRoute, navOptions) }

fun NavGraphBuilder.noticeNavGraph(
){
    composable<NoticeRoute> {
        NoticeScreen()
    }
}
