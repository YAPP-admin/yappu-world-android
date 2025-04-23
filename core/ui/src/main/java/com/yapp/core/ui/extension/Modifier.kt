package com.yapp.core.ui.extension

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

fun Modifier.borderBottom(
    color: Color,
    width: Dp = 1.dp,
): Modifier = this.then(
    Modifier.drawBehind {
        val strokeWidthPx = width.toPx()

        val yPos = size.height - (strokeWidthPx / 2)

        drawLine(
            color = color,
            start = Offset(0f, yPos),
            end = Offset(size.width, yPos),
            strokeWidth = strokeWidthPx
        )
    }
)