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
        LocalColorScheme provides LightColorScheme,
    ) {
        MaterialTheme(
            colorScheme = lightColorScheme(
                background = YappTheme.colorScheme.backgroundNormalNormal,
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

    val colorScheme: YappColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current
}
