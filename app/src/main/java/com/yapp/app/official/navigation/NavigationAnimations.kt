package com.yapp.app.official.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry


private const val ANIMATION_DURATION = 200

fun AnimatedContentTransitionScope<NavBackStackEntry>.yappEnterTransition(): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(ANIMATION_DURATION)
    ) + fadeIn(
        animationSpec = tween(ANIMATION_DURATION),
        initialAlpha = 0.3f
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.yappExitTransition(): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(ANIMATION_DURATION),
        targetOffset = { (it * 0.3f).toInt() }
    ) + fadeOut(
        animationSpec = tween(ANIMATION_DURATION),
        targetAlpha = 0.3f
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.yappPopEnterTransition(): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(ANIMATION_DURATION),
        initialOffset = { (it * 0.3f).toInt() }
    ) + fadeIn(
        animationSpec = tween(ANIMATION_DURATION),
        initialAlpha = 0.3f
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.yappPopExitTransition(): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(ANIMATION_DURATION)
    ) + fadeOut(
        animationSpec = tween(ANIMATION_DURATION),
        targetAlpha = 0.3f
    )
}