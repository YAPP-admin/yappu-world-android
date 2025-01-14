package com.yapp.core.designsystem.component.button.text

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
object TextButtonDefaults {
    val shapeMedium = RoundedCornerShape(5.dp)
    val shapeSmall = RoundedCornerShape(5.dp)

    @OptIn(ExperimentalMaterial3Api::class)
    val colorsPrimary
        @Composable
        get() = TextButtonColors(
            enableTextColor = YappTheme.colorScheme.primaryNormal,
            disableTextColor = YappTheme.colorScheme.labelDisable,
            ripple = RippleConfiguration(
                rippleAlpha = RippleAlpha(
                    pressedAlpha = 0.12f,
                    focusedAlpha = 0.88f,
                    draggedAlpha = 0.88f,
                    hoveredAlpha = 0.005f
                ),
                color = YappTheme.colorScheme.primaryNormal
            )
        )

    @OptIn(ExperimentalMaterial3Api::class)
    val colorsAssistive
        @Composable
        get() = TextButtonColors(
            enableTextColor = YappTheme.colorScheme.labelAlternative,
            disableTextColor = YappTheme.colorScheme.labelDisable,
            ripple = RippleConfiguration(
                rippleAlpha = RippleAlpha(
                    pressedAlpha = 0.12f,
                    focusedAlpha = 0.88f,
                    draggedAlpha = 0.88f,
                    hoveredAlpha = 0.005f
                ),
                color = YappTheme.colorScheme.labelNormal
            )
        )

    val textStyleMedium
        @Composable
        get() = YappTheme.typography.body2NormalBold

    val textStyleSmall
        @Composable
        get() = YappTheme.typography.label1NormalBold

    val contentPaddingsMedium = PaddingValues(vertical = 4.dp)
    val contentPaddingsSmall = PaddingValues(vertical = 4.dp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Immutable
data class TextButtonColors(
    val enableTextColor: Color,
    val disableTextColor: Color,

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
}