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
fun YappTextAssistiveButtonMedium(
    modifier: Modifier = Modifier,
    shape: Shape = TextButtonDefaults.shapeMedium,
    textStyle: TextStyle = TextButtonDefaults.textStyleMedium,
    colors: TextButtonColors = TextButtonDefaults.colorsAssistive,
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
fun YappTextAssistiveButtonSmall(
    modifier: Modifier = Modifier,
    shape: Shape = TextButtonDefaults.shapeSmall,
    textStyle: TextStyle = TextButtonDefaults.textStyleSmall,
    colors: TextButtonColors = TextButtonDefaults.colorsAssistive,
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
fun YappTextAssistiveButtonMediumPreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappTextAssistiveButtonMedium(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappTextAssistiveButtonMedium(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun YappTextAssistiveButtonSmallPreview() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappTextAssistiveButtonSmall(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappTextAssistiveButtonSmall(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}