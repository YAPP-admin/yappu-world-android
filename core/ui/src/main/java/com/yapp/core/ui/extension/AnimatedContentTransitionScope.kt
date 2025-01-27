package com.yapp.core.ui.extension

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith

fun <S> AnimatedContentTransitionScope<S>.yappDefaultAnimatedContentTransitionSpec(leftDirectionCondition: Boolean): ContentTransform {
    val direction = if (leftDirectionCondition) {
        AnimatedContentTransitionScope.SlideDirection.Left
    } else {
        AnimatedContentTransitionScope.SlideDirection.Right
    }
    return slideIntoContainer(
        towards = direction,
        animationSpec = tween(500),
    ) togetherWith slideOutOfContainer(
        towards = direction,
        animationSpec = tween(500),
    )
}
