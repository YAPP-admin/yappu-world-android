package com.yapp.core.designsystem.component.button.outlined

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Immutable
object OutlinedButtonDefaults {
    val shapeXLarge = RoundedCornerShape(12.dp)
    val shapeLarge = RoundedCornerShape(10.dp)
    val shapeMedium = RoundedCornerShape(8.dp)
    val shapeSmall = RoundedCornerShape(6.dp)
    val shapeXSmall = RoundedCornerShape(14.dp)

    @OptIn(ExperimentalMaterial3Api::class)
    val colorsPrimary
        @Composable
        get() = OutlinedButtonColors(
            enableTextColor = YappTheme.colorScheme.primaryNormal,
            disableTextColor = YappTheme.colorScheme.labelDisable,
            enableOutlinedColor = YappTheme.colorScheme.primaryNormal,
            disableOutlinedColor = YappTheme.colorScheme.lineNormalNormal,
            ripple = RippleConfiguration(
                rippleAlpha = RippleAlpha(
                    pressedAlpha = 0.18f,
                    focusedAlpha = 0.12f,
                    draggedAlpha = 0.12f,
                    hoveredAlpha = 0.075f
                ),
                color = YappTheme.colorScheme.primaryNormal
            )
        )

    val textStyleXLarge
        @Composable
        get() = YappTheme.typography.body1NormalBold

    val textStyleLarge
        @Composable
        get() = YappTheme.typography.body1NormalBold

    val textStyleMedium
        @Composable
        get() = YappTheme.typography.body2NormalBold

    val textStyleSmall
        @Composable
        get() = YappTheme.typography.label2Bold

    val textStyleXSmall
        @Composable
        get() = YappTheme.typography.label2Bold

    val contentPaddingsXLarge = PaddingValues(vertical = 16.dp, horizontal = 36.dp)
    val contentPaddingsLarge = PaddingValues(vertical = 12.dp, horizontal = 28.dp)
    val contentPaddingsMedium = PaddingValues(vertical = 9.dp, horizontal = 20.dp)
    val contentPaddingsSmall = PaddingValues(vertical = 7.dp, horizontal = 14.dp)
    val contentPaddingsXSmall = PaddingValues(vertical = 5.dp, horizontal = 12.dp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Immutable
data class OutlinedButtonColors(
    val enableTextColor: Color,
    val disableTextColor: Color,

    val enableOutlinedColor: Color,
    val disableOutlinedColor: Color,

    val ripple: RippleConfiguration,
) {
    @Stable
    internal fun textColor(
        enable: Boolean
    ): Color =
        when {
            enable -> enableTextColor
            else -> disableTextColor
        }

    @Stable
    internal fun outlinedColor(
        enable: Boolean
    ): Color =
        when {
            enable -> enableOutlinedColor
            else -> disableOutlinedColor
        }
}