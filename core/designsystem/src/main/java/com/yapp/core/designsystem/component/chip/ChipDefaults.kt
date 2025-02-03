package com.yapp.core.designsystem.component.chip

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Immutable
object ChipDefaults {
    val shapeLarge = RoundedCornerShape(12.dp)
    val shapeSmall = RoundedCornerShape(8.dp)

    val colorsFill
        @Composable get() = ChipFillColors(
            // TODO : 지정 되지 않은 컬러값 등록 필요
            mainFillTextColor = Color(0xFFFFEFE9),
            subFillTextColor = Color(0xFFFFF7EA),
            grayFillTextColor = YappTheme.colorScheme.backgroundNormalNormal,
            whiteFillTextColor = YappTheme.colorScheme.backgroundNormalNormal,
            mainFillBackGroundColor = YappTheme.colorScheme.primaryNormal,
            subFillBackGroundColor = Color(0xFFFFAD31),
            grayFillBackGroundColor = YappTheme.colorScheme.labelNeutral,
            whiteFillBackGroundColor = YappTheme.colorScheme.labelAlternative,
        )

    val colorsWeak
        @Composable get() = ChipWeakColors(
            mainWeakTextColor = YappTheme.colorScheme.primaryNormal,
            subWeakTextColor = Color(0xFFFFAD31),
            grayWeakTextColor = YappTheme.colorScheme.labelAlternative,
            whiteWeakTextColor = YappTheme.colorScheme.labelAlternative,
            mainWeakBackGroundColor = Color(0xFFFFEFE9),
            subWeakBackGroundColor = Color(0xFFFFF7EA),
            grayWeakBackGroundColor = YappTheme.colorScheme.labelDisable,
            whiteWeakBackGroundColor = YappTheme.colorScheme.backgroundNormalAlternative
        )

    val textStyleLarge
        @Composable get() = YappTheme.typography.label2Bold

    val textStyleSmall
        @Composable get() = YappTheme.typography.caption2Bold


    val contentPaddingsLarge = PaddingValues(vertical = 4.dp, horizontal = 10.dp)
    val contentPaddingsSmall = PaddingValues(vertical = 3.dp, horizontal = 8.dp)
}


enum class ChipColorType {
    Main, Sub, Gray, White
}


sealed class ChipStateType {
    internal abstract fun textColor(colorType: ChipColorType): Color
    internal abstract fun backgroundColor(colorType: ChipColorType): Color
}

@Immutable
data class ChipFillColors(
    val mainFillTextColor: Color,
    val subFillTextColor: Color,
    val grayFillTextColor: Color,
    val whiteFillTextColor: Color,
    val mainFillBackGroundColor: Color,
    val subFillBackGroundColor: Color,
    val grayFillBackGroundColor: Color,
    val whiteFillBackGroundColor: Color,
) : ChipStateType() {
    @Stable
    override fun textColor(
        colorType: ChipColorType,
    ): Color = when (colorType) {
        ChipColorType.Main -> mainFillTextColor
        ChipColorType.Sub -> subFillTextColor
        ChipColorType.White -> whiteFillTextColor
        ChipColorType.Gray -> grayFillTextColor
    }

    @Stable
    override fun backgroundColor(
        colorType: ChipColorType,
    ): Color = when (colorType) {
        ChipColorType.Main -> mainFillBackGroundColor
        ChipColorType.Sub -> subFillBackGroundColor
        ChipColorType.White -> whiteFillBackGroundColor
        ChipColorType.Gray -> grayFillBackGroundColor
    }
}


@Immutable
data class ChipWeakColors(
    val mainWeakTextColor: Color,
    val subWeakTextColor: Color,
    val grayWeakTextColor: Color,
    val whiteWeakTextColor: Color,
    val mainWeakBackGroundColor: Color,
    val subWeakBackGroundColor: Color,
    val grayWeakBackGroundColor: Color,
    val whiteWeakBackGroundColor: Color,
) : ChipStateType() {
    @Stable
    override fun textColor(
        colorType: ChipColorType,
    ): Color = when (colorType) {
        ChipColorType.Main -> mainWeakTextColor
        ChipColorType.Sub -> subWeakTextColor
        ChipColorType.White -> whiteWeakTextColor
        ChipColorType.Gray -> grayWeakTextColor
    }

    @Stable
    override fun backgroundColor(
        colorType: ChipColorType,
    ): Color = when (colorType) {
        ChipColorType.Main -> mainWeakBackGroundColor
        ChipColorType.Sub -> subWeakBackGroundColor
        ChipColorType.White -> whiteWeakBackGroundColor
        ChipColorType.Gray -> grayWeakBackGroundColor
    }
}
