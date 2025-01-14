package com.yapp.core.designsystem.component.button.text

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

@Composable
fun YappTextPrimaryButtonMedium(
    modifier: Modifier = Modifier,
    shape: Shape = TextButtonDefaults.shapeMedium,
    textStyle: TextStyle = TextButtonDefaults.textStyleMedium,
    colors: TextButtonColors = TextButtonDefaults.colorsPrimary,
    contentPaddings: PaddingValues = TextButtonDefaults.contentPaddingsMedium,
    text: String,
    enable: Boolean,
    onClick: () -> Unit,
) {
    YappTextButtonBasic(
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
fun YappTextPrimaryButtonSmall(
    modifier: Modifier = Modifier,
    shape: Shape = TextButtonDefaults.shapeSmall,
    textStyle: TextStyle = TextButtonDefaults.textStyleSmall,
    colors: TextButtonColors = TextButtonDefaults.colorsPrimary,
    contentPaddings: PaddingValues = TextButtonDefaults.contentPaddingsSmall,
    text: String,
    enable: Boolean,
    onClick: () -> Unit,
) {
    YappTextButtonBasic(
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
fun YappTextPrimaryButtonMediumPreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappTextPrimaryButtonMedium(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappTextPrimaryButtonMedium(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun YappTextPrimaryButtonSmallPreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappTextPrimaryButtonSmall(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappTextPrimaryButtonSmall(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}