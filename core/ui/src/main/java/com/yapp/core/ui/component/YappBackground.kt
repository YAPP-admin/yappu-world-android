package com.yapp.core.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun YappBackground(
    modifier: Modifier = Modifier,
    color: Color = YappTheme.colorScheme.backgroundElevatedNormal,
    content: @Composable () -> Unit,
) {
    Surface(
        color = color,
        modifier = modifier.fillMaxSize(),
    ) {
        content()
    }
}