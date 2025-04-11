package com.yapp.app.official.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.yapp.app.official.R
import kotlin.reflect.KClass

enum class TopLevelDestination(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    @StringRes val iconTextId: Int,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route,
) {
    HOME(
        selectedIcon = R.drawable.icon_home_selected,
        unselectedIcon = R.drawable.icon_home_unselected,
        iconTextId = R.string.menu_home,
        route = com.yapp.feature.home.navigation.HomeRoute::class,
    ),
    SCHEDULE(
        selectedIcon = R.drawable.icon_schedule_selected,
        unselectedIcon = R.drawable.icon_schedule_unselected,
        iconTextId = R.string.menu_schedule,
        route = com.yapp.feature.schedule.navigation.ScheduleRoute::class,
    ),
    BOARD(
        selectedIcon = R.drawable.icon_board_selected,
        unselectedIcon = R.drawable.icon_board_unselected,
        iconTextId = R.string.menu_board,
        route = com.yapp.feature.notice.navigation.NoticeRoute::class,
    ),
    PROFILE(
        selectedIcon = R.drawable.icon_profile_selected,
        unselectedIcon = R.drawable.icon_profile_unselected,
        iconTextId = R.string.menu_profile,
        route = com.yapp.feature.profile.navigation.ProfileRoute::class,
    )
}