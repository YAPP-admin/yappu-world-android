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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YappTextButtonBasic(
    modifier: Modifier,
    shape: Shape,
    text: String,
    textStyle: TextStyle,
    colors: TextButtonColors,
    contentPaddings: PaddingValues,
    enable: Boolean,
    onClick: () -> Unit,
) {
    CompositionLocalProvider(value = LocalRippleConfiguration provides colors.ripple) {
        Box(
            modifier = modifier
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