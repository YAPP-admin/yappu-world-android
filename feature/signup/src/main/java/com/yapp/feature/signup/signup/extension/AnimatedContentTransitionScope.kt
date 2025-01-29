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
        targetStep in listOf(
            SignUpStep.Complete,
            SignUpStep.Pending
        ) -> AnimatedContentTransitionScope.SlideDirection.Up

        initialStep.ordinal < targetStep.ordinal -> AnimatedContentTransitionScope.SlideDirection.Left
        else -> AnimatedContentTransitionScope.SlideDirection.Right
    }
    return slideIntoContainer(
        towards = direction,
        animationSpec = tween(500),
    ) togetherWith slideOutOfContainer(
        towards = direction,
        animationSpec = tween(500),
    )
}
