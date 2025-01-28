package com.yapp.core.designsystem.component.gradient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun GradientBottom(
    modifier: Modifier,
    color: Color,
) {
    val gradientColors = listOf(
        color,
        color.copy(alpha = 0.5f),
        Color.Transparent,
    )

    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = gradientColors,
                )
            )
    )
}

@Preview
@Composable
fun GradientBottomPreview() {
    YappTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            GradientBottom(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                color = YappTheme.colorScheme.backgroundNormalNormal,
            )
        }
    }
}