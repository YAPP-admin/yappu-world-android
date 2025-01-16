package com.yapp.core.designsystem.component.input.nestedcheckbox

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
object NestedCheckboxDefaults {

    val colors
        @Composable
        get() = NestedCheckboxColors(
            checkedTextColor = YappTheme.colorScheme.labelNormal,
            uncheckedTextColor = YappTheme.colorScheme.labelAlternative,
            checkedIconColor = YappTheme.colorScheme.primaryNormal,
            uncheckedIconColor = YappTheme.colorScheme.labelAssistive,
        )

    val textStyleNormal
        @Composable
        get() = YappTheme.typography.body2NormalRegular

    val textStyleSmall
        @Composable
        get() = YappTheme.typography.label1NormalRegular

    val iconRightSpacingNormal = 4.dp
    val iconRightSpacingSmall = 4.dp

    val iconSizeNormal = 24.dp
    val iconSizeSmall = 20.dp
}

@Immutable
data class NestedCheckboxColors(
    val checkedTextColor: Color,
    val uncheckedTextColor: Color,

    val checkedIconColor: Color,
    val uncheckedIconColor: Color,
) {
    @Stable
    internal fun textColor(
        checked: Boolean
    ): Color =
        when {
            checked -> checkedTextColor
            else -> uncheckedTextColor
        }

    @Stable
    internal fun iconColor(
        checked: Boolean
    ): Color =
        when {
            checked -> checkedIconColor
            else -> uncheckedIconColor
        }
}