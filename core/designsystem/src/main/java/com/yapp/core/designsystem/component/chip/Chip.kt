package com.yapp.core.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme


@Composable
internal fun YappChipBasic(
    modifier: Modifier,
    shape: Shape,
    text: String,
    textStyle: TextStyle,
    contentPaddings: PaddingValues,
    colorType: ChipColorType,
    colorsFill: ChipFillColors = ChipDefaults.colorsFill,
    colorsWeak: ChipWeakColors = ChipDefaults.colorsWeak,
    isFill: Boolean,
) {
    val colors = when (isFill) {
        true -> colorsFill
        false ->colorsWeak
    }

    Box(
        modifier = modifier
            .clip(shape = shape)
            .background(colors.backgroundColor(colorType))
            .padding(contentPaddings)
            .heightIn(min = textStyle.lineHeight.value.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text, style = textStyle, color = colors.textColor(colorType)
        )
    }
}


@Composable
fun YappChipLarge(
    modifier: Modifier = Modifier,
    shape: Shape = ChipDefaults.shapeLarge,
    text: String,
    textStyle: TextStyle = ChipDefaults.textStyleLarge,
    contentPaddings: PaddingValues = ChipDefaults.contentPaddingsLarge,
    colorType: ChipColorType,
    isFill: Boolean,
) {
    YappChipBasic(
        modifier = modifier,
        shape = shape,
        text = text,
        textStyle = textStyle,
        contentPaddings = contentPaddings,
        colorType = colorType,
        isFill = isFill
    )
}


@Composable
fun YappChipSmall(
    modifier: Modifier = Modifier,
    shape: Shape = ChipDefaults.shapeSmall,
    text: String,
    textStyle: TextStyle = ChipDefaults.textStyleSmall,
    contentPaddings: PaddingValues = ChipDefaults.contentPaddingsSmall,
    colorType: ChipColorType,
    colorsFill: ChipFillColors = ChipDefaults.colorsFill,
    colorsWeak: ChipWeakColors = ChipDefaults.colorsWeak,
    isFill: Boolean,
) {
    YappChipBasic(
        modifier = modifier,
        shape = shape,
        text = text,
        textStyle = textStyle,
        contentPaddings = contentPaddings,
        colorType = colorType,
        colorsFill = colorsFill,
        colorsWeak = colorsWeak,
        isFill = isFill
    )
}


@Preview(showBackground = true)
@Composable
private fun YappChipPreview() {
    YappTheme {
        Column {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                YappChipLarge(text = "chip", colorType = ChipColorType.Main, isFill = true)
                YappChipLarge(text = "chip", colorType = ChipColorType.Sub, isFill = true)
                YappChipLarge(text = "chip", colorType = ChipColorType.Gray, isFill = true)
                YappChipLarge(text = "chip", colorType = ChipColorType.White, isFill = true)
            }
            Spacer(Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                YappChipLarge(text = "chip", colorType = ChipColorType.Main, isFill = false)
                YappChipLarge(text = "chip", colorType = ChipColorType.Sub, isFill = false)
                YappChipLarge(text = "chip", colorType = ChipColorType.Gray, isFill = false)
                YappChipLarge(text = "chip", colorType = ChipColorType.White, isFill = false)
            }
            Spacer(Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                YappChipSmall(text = "chip", colorType = ChipColorType.Main, isFill = true)
                YappChipSmall(text = "chip", colorType = ChipColorType.Sub, isFill = true)
                YappChipSmall(text = "chip", colorType = ChipColorType.Gray, isFill = true)
                YappChipSmall(text = "chip", colorType = ChipColorType.White, isFill = true)
            }
            Spacer(Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                YappChipSmall(text = "chip", colorType = ChipColorType.Main, isFill = false)
                YappChipSmall(text = "chip", colorType = ChipColorType.Sub, isFill = false)
                YappChipSmall(text = "chip", colorType = ChipColorType.Gray, isFill = false)
                YappChipSmall(text = "chip", colorType = ChipColorType.White, isFill = false)
            }
        }

    }
}