package com.yapp.core.designsystem.component.button.solid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.outlined.OutlinedButtonDefaults
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YappSolidButtonBasic(
    modifier: Modifier,
    shape: Shape,
    text: String,
    textStyle: TextStyle,
    colors: SolidButtonColors,
    contentPaddings: PaddingValues,
    enable: Boolean,
    leftIcon: (@Composable () -> Unit)?,
    leftIconSpacing: Dp,
    rightIcon: (@Composable () -> Unit)?,
    rightIconSpacing: Dp,
    onClick: () -> Unit,
) {
    CompositionLocalProvider(value = LocalRippleConfiguration provides colors.ripple) {
        Box(
            modifier = modifier
                .clip(shape = shape)
                .background(colors.backgroundColor(enable = enable))
                .yappClickable(
                    runIf = enable,
                    onClick = onClick,
                )
                .padding(contentPaddings)
                .heightIn(min = textStyle.lineHeight.value.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (leftIcon != null) {
                    leftIcon()
                    Spacer(Modifier.width(leftIconSpacing))
                }

                Text(
                    text = text,
                    style = textStyle,
                    color = colors.textColor(enable = enable),
                )

                if (rightIcon != null) {
                    Spacer(Modifier.width(rightIconSpacing))
                    rightIcon()
                }
            }
        }
    }
}

@Composable
fun YappSolidPrimaryButtonXLarge(
    modifier: Modifier = Modifier,
    shape: Shape = SolidButtonDefaults.shapeXLarge,
    textStyle: TextStyle = SolidButtonDefaults.textStyleXLarge,
    colors: SolidButtonColors = SolidButtonDefaults.colorsPrimary,
    contentPaddings: PaddingValues = SolidButtonDefaults.contentPaddingsXLarge,
    text: String,
    enable: Boolean = true,
    leftIcon: (@Composable () -> Unit)? = null,
    leftIconSpacing: Dp = SolidButtonDefaults.leftIconSpacingXLarge,
    rightIcon: (@Composable () -> Unit)? = null,
    rightIconSpacing: Dp = SolidButtonDefaults.rightIconSpacingXLarge,
    onClick: () -> Unit,
) {
    YappSolidButtonBasic(
        modifier = modifier,
        shape = shape,
        text = text,
        textStyle = textStyle,
        colors = colors,
        contentPaddings = contentPaddings,
        enable = enable,
        leftIcon = leftIcon,
        leftIconSpacing = leftIconSpacing,
        rightIcon = rightIcon,
        rightIconSpacing = rightIconSpacing,
        onClick = onClick
    )
}

@Composable
fun YappSolidPrimaryButtonLarge(
    modifier: Modifier = Modifier,
    shape: Shape = SolidButtonDefaults.shapeLarge,
    textStyle: TextStyle = SolidButtonDefaults.textStyleLarge,
    colors: SolidButtonColors = SolidButtonDefaults.colorsPrimary,
    contentPaddings: PaddingValues = SolidButtonDefaults.contentPaddingsLarge,
    text: String,
    enable: Boolean = true,
    leftIcon: (@Composable () -> Unit)? = null,
    leftIconSpacing: Dp = SolidButtonDefaults.leftIconSpacingLarge,
    rightIcon: (@Composable () -> Unit)? = null,
    rightIconSpacing: Dp = SolidButtonDefaults.rightIconSpacingLarge,
    onClick: () -> Unit,
) {
    YappSolidButtonBasic(
        modifier = modifier,
        shape = shape,
        text = text,
        textStyle = textStyle,
        colors = colors,
        contentPaddings = contentPaddings,
        enable = enable,
        leftIcon = leftIcon,
        leftIconSpacing = leftIconSpacing,
        rightIcon = rightIcon,
        rightIconSpacing = rightIconSpacing,
        onClick = onClick
    )
}

@Composable
fun YappSolidPrimaryButtonSmall(
    modifier: Modifier = Modifier,
    shape: Shape = SolidButtonDefaults.shapeSmall,
    textStyle: TextStyle = SolidButtonDefaults.textStyleSmall,
    colors: SolidButtonColors = SolidButtonDefaults.colorsPrimary,
    contentPaddings: PaddingValues = SolidButtonDefaults.contentPaddingsSmall,
    text: String,
    enable: Boolean = true,
    leftIcon: (@Composable () -> Unit)? = null,
    leftIconSpacing: Dp = OutlinedButtonDefaults.leftIconSpacingSmall,
    rightIcon: (@Composable () -> Unit)? = null,
    rightIconSpacing: Dp = OutlinedButtonDefaults.rightIconSpacingSmall,
    onClick: () -> Unit,
) {
    YappSolidButtonBasic(
        modifier = modifier,
        shape = shape,
        text = text,
        textStyle = textStyle,
        colors = colors,
        contentPaddings = contentPaddings,
        enable = enable,
        leftIcon = leftIcon,
        leftIconSpacing = leftIconSpacing,
        rightIcon = rightIcon,
        rightIconSpacing = rightIconSpacing,
        onClick = onClick
    )
}

@Composable
fun YappSolidPrimaryButtonXSmall(
    modifier: Modifier = Modifier,
    shape: Shape = SolidButtonDefaults.shapeXSmall,
    textStyle: TextStyle = SolidButtonDefaults.textStyleXSmall,
    colors: SolidButtonColors = SolidButtonDefaults.colorsPrimary,
    contentPaddings: PaddingValues = SolidButtonDefaults.contentPaddingsXSmall,
    text: String,
    enable: Boolean = true,
    leftIcon: (@Composable () -> Unit)? = null,
    leftIconSpacing: Dp = OutlinedButtonDefaults.leftIconSpacingXSmall,
    rightIcon: (@Composable () -> Unit)? = null,
    rightIconSpacing: Dp = OutlinedButtonDefaults.rightIconSpacingXSmall,
    onClick: () -> Unit,
) {
    YappSolidButtonBasic(
        modifier = modifier,
        shape = shape,
        text = text,
        textStyle = textStyle,
        colors = colors,
        contentPaddings = contentPaddings,
        enable = enable,
        leftIcon = leftIcon,
        leftIconSpacing = leftIconSpacing,
        rightIcon = rightIcon,
        rightIconSpacing = rightIconSpacing,
        onClick = onClick
    )
}

@Preview
@Composable
private fun YappSolidPrimaryButtonXLargePreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappSolidPrimaryButtonXLarge(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappSolidPrimaryButtonXLarge(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun YappSolidPrimaryButtonLargePreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappSolidPrimaryButtonLarge(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappSolidPrimaryButtonLarge(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun YappSolidPrimaryButtonSmallPreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappSolidPrimaryButtonSmall(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappSolidPrimaryButtonSmall(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun YappSolidPrimaryButtonXSmallPreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappSolidPrimaryButtonXSmall(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappSolidPrimaryButtonXSmall(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}
