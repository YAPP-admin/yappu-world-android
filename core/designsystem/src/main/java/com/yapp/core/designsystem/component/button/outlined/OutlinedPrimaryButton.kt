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
fun YappOutlinedPrimaryButtonXLarge(
    modifier: Modifier = Modifier,
    shape: Shape = OutlinedButtonDefaults.shapeXLarge,
    textStyle: TextStyle = OutlinedButtonDefaults.textStyleXLarge,
    colors: OutlinedButtonColors = OutlinedButtonDefaults.colorsPrimary,
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
fun YappOutlinedPrimaryButtonLarge(
    modifier: Modifier = Modifier,
    shape: Shape = OutlinedButtonDefaults.shapeLarge,
    textStyle: TextStyle = OutlinedButtonDefaults.textStyleLarge,
    colors: OutlinedButtonColors = OutlinedButtonDefaults.colorsPrimary,
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

@Preview(showBackground = true)
@Composable
private fun YappOutlinedPrimaryButtonXLargePreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappOutlinedPrimaryButtonXLarge(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappOutlinedPrimaryButtonXLarge(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun YappOutlinedPrimaryButtonLargePreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappOutlinedPrimaryButtonLarge(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappOutlinedPrimaryButtonLarge(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}