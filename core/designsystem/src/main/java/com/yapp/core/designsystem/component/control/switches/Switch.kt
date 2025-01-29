package com.yapp.core.designsystem.component.control.switches

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun YappSwitchBasic(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    colors: SwitchColors = SwitchDefaults.colors,
    trackWidth: Dp,
    trackHeight: Dp,
    cornerRadius: Dp,
    thumbSize: Dp,
    thumbPadding: Dp,
) {
    val trackColor = colors.trackColor(checked)
    val thumbColor = colors.thumbColor(checked)

    val offsetAnimation by animateDpAsState(
        targetValue = if (checked) {
            trackWidth - thumbSize - thumbPadding * 2
        } else {
            0.dp
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "offsetAnimation",
    )

    Box {
        Box(
            modifier = modifier
                .width(trackWidth)
                .height(trackHeight)
                .clip(RoundedCornerShape(cornerRadius))
                .background(trackColor)
                .yappClickable(
                    rippleEnabled = false,
                    runIf = enabled,
                    onClick = { onCheckedChange(!checked) }
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .padding(thumbPadding)
                    .offset {
                        IntOffset(offsetAnimation.roundToPx(), 0)
                    }
                    .size(thumbSize)
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(thumbColor)
            )
        }

        if (enabled.not()) {
            Box(
                modifier = Modifier.matchParentSize()
                    .background(colors.disabledMaskColor)
            )
        }
    }
}

@Composable
fun YappSwitchMedium(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    colors: SwitchColors = SwitchDefaults.colors,
    trackWidth: Dp = SwitchDefaults.trackWidthMedium,
    trackHeight: Dp = SwitchDefaults.trackHeightMedium,
    cornerRadius: Dp = SwitchDefaults.cornerRadiusMedium,
    thumbSize: Dp = SwitchDefaults.thumbSizeMedium,
    thumbPadding: Dp = SwitchDefaults.thumbPaddingMedium,
) {
    YappSwitchBasic(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        modifier = modifier,
        colors = colors,
        trackWidth = trackWidth,
        trackHeight = trackHeight,
        cornerRadius = cornerRadius,
        thumbSize = thumbSize,
        thumbPadding = thumbPadding,
    )
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun YappSwitchMediumPreview() {
    YappTheme {
        var checked by remember { mutableStateOf(false) }
        YappSwitchMedium(
            checked = checked,
            enabled = true,
            onCheckedChange = { checked = it },
        )
    }
}
