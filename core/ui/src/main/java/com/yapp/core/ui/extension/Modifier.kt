package com.yapp.core.ui.extension

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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

fun Modifier.dashedBorder(
    color: Color,
    strokeWidth: Dp = 1.dp,
    dashLength: Dp = 10.dp,
    gapLength: Dp = 5.dp,
    cornerRadius: Dp = 0.dp
): Modifier = drawBehind {
    val stroke = Stroke(
        width = strokeWidth.toPx(),
        pathEffect = PathEffect.dashPathEffect(
            floatArrayOf(dashLength.toPx(), gapLength.toPx()),
            0f
        )
    )
    drawRoundRect(
        color = color,
        size = size,
        cornerRadius = CornerRadius(cornerRadius.toPx()),
        style = stroke
    )
}