package com.yapp.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun YappTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalTypography provides Typography,
        LocalLightColorScheme provides LightColorScheme,
    ) {
        MaterialTheme(
            colorScheme = lightColorScheme(
                background = YappTheme.lightColorScheme.backgroundNormalNormal,
            ),
            content = content,
        )
    }
}

object YappTheme {
    val typography: YappTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val lightColorScheme: YappColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalLightColorScheme.current
}
