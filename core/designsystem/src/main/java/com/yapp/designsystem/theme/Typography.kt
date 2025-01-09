package com.yapp.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.yapp.designsystem.R

internal val pretendardFamily = FontFamily(
    Font(R.font.pretendard, FontWeight(700)),
    Font(R.font.pretendard, FontWeight(600)),
    Font(R.font.pretendard, FontWeight(400)),
)

private val pretendardStyle = TextStyle(
    fontFamily = pretendardFamily,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false,
    ),
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None,
    ),
)

internal val Typography = YappTypography(
    display1 = pretendardStyle.copy(
        fontWeight = FontWeight(700),
        fontSize = 56.sp,
        lineHeight = 72.sp,
        letterSpacing = TextUnit(-0.0319f, TextUnitType.Em),
    ),
    display2 = pretendardStyle.copy(
        fontWeight = FontWeight(700),
        fontSize = 40.sp,
        lineHeight = 52.sp,
        letterSpacing = TextUnit(-0.0282f, TextUnitType.Em),
    ),
    title1 = pretendardStyle.copy(
        fontWeight = FontWeight(700),
        fontSize = 36.sp,
        lineHeight = 48.sp,
        letterSpacing = TextUnit(-0.027f, TextUnitType.Em),
    ),
    title2 = pretendardStyle.copy(
        fontWeight = FontWeight(700),
        fontSize = 28.sp,
        lineHeight = 38.sp,
        letterSpacing = TextUnit(-0.0236f, TextUnitType.Em),
    ),
    title3 = pretendardStyle.copy(
        fontWeight = FontWeight(700),
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = TextUnit(-0.023f, TextUnitType.Em),
    ),
    heading1 = pretendardStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 22.sp,
        lineHeight = 30.sp,
        letterSpacing = TextUnit(-0.0194f, TextUnitType.Em),
    ),
    heading2 = pretendardStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = TextUnit(-0.012f, TextUnitType.Em),
    ),
    headline1 = pretendardStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = TextUnit(-0.002f, TextUnitType.Em),
    ),
    headline2 = pretendardStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = TextUnit(0f, TextUnitType.Em),
    ),
    body1Normal = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = TextUnit(0.0057f, TextUnitType.Em),
    ),
    body1Reading = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = TextUnit(0.0057f, TextUnitType.Em),
    ),
    body2Normal = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 15.sp,
        lineHeight = 22.sp,
        letterSpacing = TextUnit(0.0096f, TextUnitType.Em),
    ),
    body2Reading = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 15.sp,
        lineHeight = 24.sp,
        letterSpacing = TextUnit(0.0096f, TextUnitType.Em),
    ),
    label1Normal = pretendardStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = TextUnit(0.0145f, TextUnitType.Em),
    ),
    label1Reading = pretendardStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = TextUnit(0.0145f, TextUnitType.Em),
    ),
    label2 = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = TextUnit(0.0194f, TextUnitType.Em),
    ),
    caption1 = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = TextUnit(0.0252f, TextUnitType.Em),
    ),
    caption2 = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = TextUnit(0.0311f, TextUnitType.Em),
    ),
)

@Immutable
data class YappTypography(
    val display1: TextStyle,
    val display2: TextStyle,

    val title1: TextStyle,
    val title2: TextStyle,
    val title3: TextStyle,

    val heading1: TextStyle,
    val heading2: TextStyle,

    val headline1: TextStyle,
    val headline2: TextStyle,

    val body1Normal: TextStyle,
    val body1Reading: TextStyle,
    val body2Normal: TextStyle,
    val body2Reading: TextStyle,

    val label1Normal: TextStyle,
    val label1Reading: TextStyle,
    val label2: TextStyle,

    val caption1: TextStyle,
    val caption2: TextStyle,
)

val LocalTypography = staticCompositionLocalOf {
    YappTypography(
        display1 = pretendardStyle,
        display2 = pretendardStyle,
        title1 = pretendardStyle,
        title2 = pretendardStyle,
        title3 = pretendardStyle,
        heading1 = pretendardStyle,
        heading2 = pretendardStyle,
        headline1 = pretendardStyle,
        headline2 = pretendardStyle,
        body1Normal = pretendardStyle,
        body1Reading = pretendardStyle,

        body2Normal = pretendardStyle,
        body2Reading = pretendardStyle,
        label1Normal = pretendardStyle,
        label1Reading = pretendardStyle,
        label2 = pretendardStyle,
        caption1 = pretendardStyle,
        caption2 = pretendardStyle,
    )
}
