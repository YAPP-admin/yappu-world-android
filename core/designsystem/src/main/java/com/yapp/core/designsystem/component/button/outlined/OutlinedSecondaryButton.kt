package com.yapp.core.designsystem.component.button.outlined

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun YappOutlinedSecondaryButtonXLarge(
    modifier: Modifier = Modifier,
    shape: Shape = OutlinedButtonDefaults.shapeXLarge,
    textStyle: TextStyle = OutlinedButtonDefaults.textStyleXLarge,
    colors: OutlinedButtonColors = OutlinedButtonDefaults.colorsSecondary,
    contentPaddings: PaddingValues = OutlinedButtonDefaults.contentPaddingsXLarge,
    text: String,
    enable: Boolean = true,
    leftIcon: (@Composable () -> Unit)? = null,
    leftIconSpacing: Dp = OutlinedButtonDefaults.leftIconSpacingXLarge,
    rightIcon: (@Composable () -> Unit)? = null,
    rightIconSpacing: Dp = OutlinedButtonDefaults.rightIconSpacingXLarge,
    onClick: () -> Unit,
) {
    YappOutlinedButtonBasic(
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
fun YappOutlinedSecondaryButtonLarge(
    modifier: Modifier = Modifier,
    shape: Shape = OutlinedButtonDefaults.shapeLarge,
    textStyle: TextStyle = OutlinedButtonDefaults.textStyleLarge,
    colors: OutlinedButtonColors = OutlinedButtonDefaults.colorsSecondary,
    contentPaddings: PaddingValues = OutlinedButtonDefaults.contentPaddingsLarge,
    text: String,
    enable: Boolean = true,
    leftIcon: (@Composable () -> Unit)? = null,
    leftIconSpacing: Dp = OutlinedButtonDefaults.leftIconSpacingLarge,
    rightIcon: (@Composable () -> Unit)? = null,
    rightIconSpacing: Dp = OutlinedButtonDefaults.rightIconSpacingLarge,
    onClick: () -> Unit,
) {
    YappOutlinedButtonBasic(
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

@Preview(showBackground = true)
@Composable
private fun YappOutlinedSecondaryButtonXLargePreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappOutlinedSecondaryButtonXLarge(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappOutlinedSecondaryButtonXLarge(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun YappOutlinedSecondaryButtonLargePreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappOutlinedSecondaryButtonLarge(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappOutlinedSecondaryButtonLarge(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}