package com.yapp.core.designsystem.component.button.outlined

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YappOutlinedButtonBasic(
    modifier: Modifier,
    shape: Shape,
    text: String,
    textStyle: TextStyle,
    colors: OutlinedButtonColors,
    contentPaddings: PaddingValues,
    enable: Boolean,
    onClick: () -> Unit,
) {
    CompositionLocalProvider(value = LocalRippleConfiguration provides colors.ripple) {
        Box(
            modifier = modifier
                .border(width = 1.dp, color = colors.outlinedColor(enable = enable), shape = shape)
                .clip(shape = shape)
                .yappClickable(
                    runIf = enable,
                    onClick = onClick,
                )
                .padding(contentPaddings)
                .heightIn(min = textStyle.lineHeight.value.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                style = textStyle,
                color = colors.textColor(enable = enable),
            )
        }
    }
}

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