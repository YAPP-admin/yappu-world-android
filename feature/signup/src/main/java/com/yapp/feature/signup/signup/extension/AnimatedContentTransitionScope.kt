package com.yapp.feature.signup.signup.extension

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import com.yapp.feature.signup.signup.SignUpStep

fun AnimatedContentTransitionScope<SignUpStep>.signUpAnimatedContentTransitionSpec(
    initialStep: SignUpStep,
    targetStep: SignUpStep,
): ContentTransform {
    val direction = when {
        initialStep.ordinal < targetStep.ordinal -> AnimatedContentTransitionScope.SlideDirection.Left
        else -> AnimatedContentTransitionScope.SlideDirection.Right
    }

    val durationMillis = if (initialStep == SignUpStep.Name && targetStep == SignUpStep.Pending) 0 else 500

    return slideIntoContainer(
        towards = direction,
        animationSpec = tween(durationMillis),
    ) togetherWith slideOutOfContainer(
        towards = direction,
        animationSpec = tween(durationMillis),
    )
}
