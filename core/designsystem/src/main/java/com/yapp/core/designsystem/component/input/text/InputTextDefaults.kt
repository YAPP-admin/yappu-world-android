package com.yapp.core.designsystem.component.input.text

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Immutable
object InputTextDefaults {
    val shapeLarge = RoundedCornerShape(10.dp)
    val shapeMedium = RoundedCornerShape(8.dp)

    val colors
        @Composable
        get() = InputTextColors(
            labelColor = YappTheme.colorScheme.labelAlternative,

            defaultInputTextColor = YappTheme.colorScheme.labelNormal.copy(alpha = 0.16f),
            activeInputTextColor = YappTheme.colorScheme.labelNormal,
            errorInputTextColor = YappTheme.colorScheme.statusNegative,
            successInputTextColor = YappTheme.colorScheme.labelNormal,

            defaultDescriptionColor = YappTheme.colorScheme.labelAssistive,
            activeDescriptionColor = YappTheme.colorScheme.staticBlack,
            errorDescriptionColor = YappTheme.colorScheme.statusNegative,
            successDescriptionColor = YappTheme.colorScheme.statusPositive,

            defaultOutlineColor = YappTheme.colorScheme.lineNormalNormal,
            activeOutlineColor = YappTheme.colorScheme.primaryNormal,
            errorOutlineColor = YappTheme.colorScheme.statusNegative,
            successOutlineColor = YappTheme.colorScheme.lineNormalStrong,
        )

    val textStylesLarge
        @Composable
        get() = InputTextTextStyles(
            labelTextStyle = YappTheme.typography.label1NormalMedium,
            inputTextTextStyle = YappTheme.typography.body1NormalRegular,
            descriptionTextStyle = YappTheme.typography.label2Regular,
        )

    val textStylesMedium
        @Composable
        get() = InputTextTextStyles(
            labelTextStyle = YappTheme.typography.label1NormalMedium,
            inputTextTextStyle = YappTheme.typography.body2NormalRegular,
            descriptionTextStyle = YappTheme.typography.label2Regular,
        )

    val spacingsLarge = InputTextSpacings(
        labelBottomSpacing = 4.dp,
        rightIconStartSpacing = 12.dp,
        descriptionTopSpacing = 8.dp,
    )

    // TODO 아직 정의되지 않음
    val spacingsMedium = InputTextSpacings(
        labelBottomSpacing = Dp.Unspecified,
        rightIconStartSpacing = Dp.Unspecified,
        descriptionTopSpacing = Dp.Unspecified,
    )

    val contentPaddingsLarge = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
    val contentPaddingsMedium = PaddingValues(vertical = 8.dp, horizontal = 10.dp)

    val rightIconSizeLarge = 24.dp
    // TODO 아직 정의되지 않음
    val rightIconSizeMedium = Dp.Unspecified
}

@Immutable
data class InputTextColors(
    val labelColor: Color,

    val defaultInputTextColor: Color,
    val activeInputTextColor: Color,
    val errorInputTextColor: Color,
    val successInputTextColor: Color,

    val defaultDescriptionColor: Color,
    val activeDescriptionColor: Color,
    val errorDescriptionColor: Color,
    val successDescriptionColor: Color,

    val defaultOutlineColor: Color,
    val activeOutlineColor: Color,
    val errorOutlineColor: Color,
    val successOutlineColor: Color,
) {
    @Stable
    internal fun inputTextColor(
        value: String,
        isError: Boolean,
        focused: Boolean,
    ): Color =
        when {
            isError -> errorInputTextColor
            value.isNotEmpty() && focused.not() -> successInputTextColor
            focused -> activeInputTextColor
            else -> successInputTextColor
        }

    @Stable
    internal fun descriptionColor(
        value: String,
        isError: Boolean,
        focused: Boolean,
    ): Color =
        when {
            isError -> errorDescriptionColor
            value.isNotEmpty() && focused.not() -> successDescriptionColor
            focused -> activeDescriptionColor
            else -> defaultDescriptionColor
        }

    @Stable
    internal fun outlineColor(
        value: String,
        isError: Boolean,
        focused: Boolean,
    ): Color =
        when {
            isError -> errorOutlineColor
            value.isNotEmpty() && focused.not() -> successOutlineColor
            focused -> activeOutlineColor
            else -> defaultOutlineColor
        }
}

@Immutable
data class InputTextSpacings(
    val labelBottomSpacing: Dp,
    val rightIconStartSpacing: Dp,
    val descriptionTopSpacing: Dp,
)

@Immutable
data class InputTextTextStyles(
    val labelTextStyle: TextStyle,
    val inputTextTextStyle: TextStyle,
    val descriptionTextStyle: TextStyle,
)