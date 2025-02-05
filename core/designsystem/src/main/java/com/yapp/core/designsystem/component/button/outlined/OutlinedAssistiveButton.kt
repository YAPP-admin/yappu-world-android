package com.yapp.core.designsystem.component.button.outlined

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun YappOutlinedAssistiveButtonXLarge(
    modifier: Modifier = Modifier,
    shape: Shape = OutlinedButtonDefaults.shapeXLarge,
    textStyle: TextStyle = OutlinedButtonDefaults.textStyleXLarge,
    colors: OutlinedButtonColors = OutlinedButtonDefaults.colorsAssistive,
    contentPaddings: PaddingValues = OutlinedButtonDefaults.contentPaddingsXLarge,
    text: String,
    enable: Boolean = true,
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
        onClick = onClick
    )
}

@Composable
fun YappOutlinedAssistiveButtonLarge(
    modifier: Modifier = Modifier,
    shape: Shape = OutlinedButtonDefaults.shapeLarge,
    textStyle: TextStyle = OutlinedButtonDefaults.textStyleLarge,
    colors: OutlinedButtonColors = OutlinedButtonDefaults.colorsAssistive,
    contentPaddings: PaddingValues = OutlinedButtonDefaults.contentPaddingsLarge,
    text: String,
    enable: Boolean = true,
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
        onClick = onClick
    )
}

@Composable
fun YappOutlinedAssistiveButtonMedium(
    modifier: Modifier = Modifier,
    shape: Shape = OutlinedButtonDefaults.shapeMedium,
    textStyle: TextStyle = OutlinedButtonDefaults.textStyleMedium,
    colors: OutlinedButtonColors = OutlinedButtonDefaults.colorsAssistive,
    contentPaddings: PaddingValues = OutlinedButtonDefaults.contentPaddingsMedium,
    text: String,
    enable: Boolean = true,
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
        onClick = onClick
    )
}

@Composable
fun YappOutlinedAssistiveButtonXSmall(
    modifier: Modifier = Modifier,
    shape: Shape = OutlinedButtonDefaults.shapeXSmall,
    textStyle: TextStyle = OutlinedButtonDefaults.textStyleXSmall,
    colors: OutlinedButtonColors = OutlinedButtonDefaults.colorsAssistive,
    contentPaddings: PaddingValues = OutlinedButtonDefaults.contentPaddingsXSmall,
    text: String,
    enable: Boolean = true,
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
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
private fun YappOutlinedAssistiveButtonXLargePreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappOutlinedAssistiveButtonXLarge(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappOutlinedAssistiveButtonXLarge(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun YappOutlinedAssistiveButtonLargePreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappOutlinedAssistiveButtonLarge(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappOutlinedAssistiveButtonLarge(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun YappOutlinedAssistiveButtonMediumPreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappOutlinedAssistiveButtonMedium(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappOutlinedAssistiveButtonMedium(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun YappOutlinedAssistiveButtonXSmallPreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappOutlinedAssistiveButtonXSmall(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappOutlinedAssistiveButtonXSmall(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}