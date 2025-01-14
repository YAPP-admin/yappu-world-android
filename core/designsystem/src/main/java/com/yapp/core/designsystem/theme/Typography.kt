package com.yapp.core.designsystem.theme

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
import com.yapp.core.designsystem.R

internal val pretendardFamily = FontFamily(
    Font(R.font.pretendardbold, FontWeight(700)),
    Font(R.font.pretendardsemibold, FontWeight(600)),
    Font(R.font.pretendardmedium, FontWeight(500)),
    Font(R.font.pretendardregular, FontWeight(400)),
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
    display1Bold = pretendardStyle.copy(
        fontWeight = FontWeight(700),
        fontSize = 56.sp,
        lineHeight = 72.sp,
        letterSpacing = TextUnit(-0.0319f, TextUnitType.Em),
    ),
    display1Medium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 56.sp,
        lineHeight = 72.sp,
        letterSpacing = TextUnit(-0.0319f, TextUnitType.Em),
    ),
    display1Regular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 56.sp,
        lineHeight = 72.sp,
        letterSpacing = TextUnit(-0.0319f, TextUnitType.Em),
    ),
    display2Bold = pretendardStyle.copy(
        fontWeight = FontWeight(700),
        fontSize = 40.sp,
        lineHeight = 52.sp,
        letterSpacing = TextUnit(-0.0282f, TextUnitType.Em),
    ),
    display2Medium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 40.sp,
        lineHeight = 52.sp,
        letterSpacing = TextUnit(-0.0282f, TextUnitType.Em),
    ),
    display2Regular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 40.sp,
        lineHeight = 52.sp,
        letterSpacing = TextUnit(-0.0282f, TextUnitType.Em),
    ),
    title1Bold = pretendardStyle.copy(
        fontWeight = FontWeight(700),
        fontSize = 36.sp,
        lineHeight = 48.sp,
        letterSpacing = TextUnit(-0.027f, TextUnitType.Em),
    ),
    title1Medium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 36.sp,
        lineHeight = 48.sp,
        letterSpacing = TextUnit(-0.027f, TextUnitType.Em),
    ),
    title1Regular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 36.sp,
        lineHeight = 48.sp,
        letterSpacing = TextUnit(-0.027f, TextUnitType.Em),
    ),
    title2Bold = pretendardStyle.copy(
        fontWeight = FontWeight(700),
        fontSize = 28.sp,
        lineHeight = 38.sp,
        letterSpacing = TextUnit(-0.0236f, TextUnitType.Em),
    ),
    title2Medium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 28.sp,
        lineHeight = 38.sp,
        letterSpacing = TextUnit(-0.0236f, TextUnitType.Em),
    ),
    title2Regular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 28.sp,
        lineHeight = 38.sp,
        letterSpacing = TextUnit(-0.0236f, TextUnitType.Em),
    ),
    title3Bold = pretendardStyle.copy(
        fontWeight = FontWeight(700),
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = TextUnit(-0.023f, TextUnitType.Em),
    ),
    title3Medium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = TextUnit(-0.023f, TextUnitType.Em),
    ),
    title3Regular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = TextUnit(-0.023f, TextUnitType.Em),
    ),
    heading1Bold = pretendardStyle.copy(
        fontWeight = FontWeight(700),
        fontSize = 22.sp,
        lineHeight = 30.sp,
        letterSpacing = TextUnit(-0.0194f, TextUnitType.Em),
    ),
    heading1Medium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 22.sp,
        lineHeight = 30.sp,
        letterSpacing = TextUnit(-0.0194f, TextUnitType.Em),
    ),
    heading1Regular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 22.sp,
        lineHeight = 30.sp,
        letterSpacing = TextUnit(-0.0194f, TextUnitType.Em),
    ),
    heading2Bold = pretendardStyle.copy(
        fontWeight = FontWeight(700),
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = TextUnit(-0.012f, TextUnitType.Em),
    ),
    heading2Medium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = TextUnit(-0.012f, TextUnitType.Em),
    ),
    heading2Regular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = TextUnit(-0.012f, TextUnitType.Em),
    ),
    headline1Bold = pretendardStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = TextUnit(-0.002f, TextUnitType.Em),
    ),
    headline1Medium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = TextUnit(-0.002f, TextUnitType.Em),
    ),
    headline1Regular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = TextUnit(-0.002f, TextUnitType.Em),
    ),
    headline2Bold = pretendardStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = TextUnit(0f, TextUnitType.Em),
    ),
    headline2Medium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = TextUnit(0f, TextUnitType.Em),
    ),
    headline2Regular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = TextUnit(0f, TextUnitType.Em),
    ),
    body1NormalBold = pretendardStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = TextUnit(0.0057f, TextUnitType.Em),
    ),
    body1NormalMedium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = TextUnit(0.0057f, TextUnitType.Em),
    ),
    body1NormalRegular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = TextUnit(0.0057f, TextUnitType.Em),
    ),
    body1ReadingBold = pretendardStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = TextUnit(0.0057f, TextUnitType.Em),
    ),
    body1ReadingMedium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = TextUnit(0.0057f, TextUnitType.Em),
    ),
    body1ReadingRegular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = TextUnit(0.0057f, TextUnitType.Em),
    ),
    body2NormalBold = pretendardStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 15.sp,
        lineHeight = 22.sp,
        letterSpacing = TextUnit(0.0096f, TextUnitType.Em),
    ),
    body2NormalMedium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 15.sp,
        lineHeight = 22.sp,
        letterSpacing = TextUnit(0.0096f, TextUnitType.Em),
    ),
    body2NormalRegular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 15.sp,
        lineHeight = 22.sp,
        letterSpacing = TextUnit(0.0096f, TextUnitType.Em),
    ),
    body2ReadingBold = pretendardStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 15.sp,
        lineHeight = 24.sp,
        letterSpacing = TextUnit(0.0096f, TextUnitType.Em),
    ),
    body2ReadingMedium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 15.sp,
        lineHeight = 24.sp,
        letterSpacing = TextUnit(0.0096f, TextUnitType.Em),
    ),
    body2ReadingRegular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 15.sp,
        lineHeight = 24.sp,
        letterSpacing = TextUnit(0.0096f, TextUnitType.Em),
    ),
    label1NormalBold = pretendardStyle.copy(
        fontWeight = FontWeight(700),
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = TextUnit(0.0145f, TextUnitType.Em),
    ),
    label1NormalMedium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = TextUnit(0.0145f, TextUnitType.Em),
    ),
    label1NormalRegular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = TextUnit(0.0145f, TextUnitType.Em),
    ),
    label1ReadingBold = pretendardStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = TextUnit(0.0145f, TextUnitType.Em),
    ),
    label1ReadingMedium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = TextUnit(0.0145f, TextUnitType.Em),
    ),
    label1ReadingRegular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = TextUnit(0.0145f, TextUnitType.Em),
    ),
    label2Bold = pretendardStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = TextUnit(0.0194f, TextUnitType.Em),
    ),
    label2Medium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = TextUnit(0.0194f, TextUnitType.Em),
    ),
    label2Regular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = TextUnit(0.0194f, TextUnitType.Em),
    ),
    caption1Bold = pretendardStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = TextUnit(0.0252f, TextUnitType.Em),
    ),
    caption1Medium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = TextUnit(0.0252f, TextUnitType.Em),
    ),
    caption1Regular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = TextUnit(0.0252f, TextUnitType.Em),
    ),
    caption2Bold = pretendardStyle.copy(
        fontWeight = FontWeight(600),
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = TextUnit(0.0311f, TextUnitType.Em),
    ),
    caption2Medium = pretendardStyle.copy(
        fontWeight = FontWeight(500),
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = TextUnit(0.0311f, TextUnitType.Em),
    ),
    caption2Regular = pretendardStyle.copy(
        fontWeight = FontWeight(400),
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = TextUnit(0.0311f, TextUnitType.Em),
    ),
)


@Immutable
data class YappTypography(
    val display1Bold: TextStyle,
    val display1Medium: TextStyle,
    val display1Regular: TextStyle,

    val display2Bold: TextStyle,
    val display2Medium: TextStyle,
    val display2Regular: TextStyle,

    val title1Bold: TextStyle,
    val title1Medium: TextStyle,
    val title1Regular: TextStyle,

    val title2Bold: TextStyle,
    val title2Regular: TextStyle,
    val title2Medium: TextStyle,

    val title3Bold: TextStyle,
    val title3Medium: TextStyle,
    val title3Regular: TextStyle,

    val heading1Bold: TextStyle,
    val heading1Medium: TextStyle,
    val heading1Regular: TextStyle,

    val heading2Bold: TextStyle,
    val heading2Medium: TextStyle,
    val heading2Regular: TextStyle,

    val headline1Bold: TextStyle,
    val headline1Medium: TextStyle,
    val headline1Regular: TextStyle,
    
    val headline2Bold: TextStyle,
    val headline2Medium: TextStyle,
    val headline2Regular: TextStyle,

    val body1NormalBold: TextStyle,
    val body1NormalMedium: TextStyle,
    val body1NormalRegular: TextStyle,
    
    val body1ReadingBold: TextStyle,
    val body1ReadingMedium: TextStyle,
    val body1ReadingRegular: TextStyle,
    
    val body2NormalBold: TextStyle,
    val body2NormalMedium: TextStyle,
    val body2NormalRegular: TextStyle,
    
    val body2ReadingBold: TextStyle,
    val body2ReadingMedium: TextStyle,
    val body2ReadingRegular: TextStyle,

    val label1NormalBold: TextStyle,
    val label1NormalMedium: TextStyle,
    val label1NormalRegular: TextStyle,
    
    val label1ReadingBold: TextStyle,
    val label1ReadingMedium: TextStyle,
    val label1ReadingRegular: TextStyle,
    
    val label2Bold: TextStyle,
    val label2Medium: TextStyle,
    val label2Regular: TextStyle,

    val caption1Bold: TextStyle,
    val caption1Medium: TextStyle,
    val caption1Regular: TextStyle,
    
    val caption2Bold: TextStyle,
    val caption2Medium: TextStyle,
    val caption2Regular: TextStyle,
)

val LocalTypography = staticCompositionLocalOf {
    YappTypography(
        display1Bold = pretendardStyle,
        display1Medium = pretendardStyle,
        display1Regular = pretendardStyle,
        display2Bold = pretendardStyle,
        display2Medium = pretendardStyle,
        display2Regular = pretendardStyle,
        title1Bold = pretendardStyle,
        title1Medium = pretendardStyle,
        title1Regular = pretendardStyle,
        title2Bold = pretendardStyle,
        title2Regular = pretendardStyle,
        title2Medium = pretendardStyle,
        title3Bold = pretendardStyle,
        title3Medium = pretendardStyle,
        title3Regular = pretendardStyle,
        heading1Bold = pretendardStyle,
        heading1Medium = pretendardStyle,
        heading1Regular = pretendardStyle,
        heading2Bold = pretendardStyle,
        heading2Medium = pretendardStyle,
        heading2Regular = pretendardStyle,
        headline1Bold = pretendardStyle,
        headline1Medium = pretendardStyle,
        headline1Regular = pretendardStyle,
        headline2Bold = pretendardStyle,
        headline2Medium = pretendardStyle,
        headline2Regular = pretendardStyle,
        body1NormalBold = pretendardStyle,
        body1NormalMedium = pretendardStyle,
        body1NormalRegular = pretendardStyle,
        body1ReadingBold = pretendardStyle,
        body1ReadingMedium = pretendardStyle,
        body1ReadingRegular = pretendardStyle,
        body2NormalBold = pretendardStyle,
        body2NormalMedium = pretendardStyle,
        body2NormalRegular = pretendardStyle,
        body2ReadingBold = pretendardStyle,
        body2ReadingMedium = pretendardStyle,
        body2ReadingRegular = pretendardStyle,
        label1NormalBold = pretendardStyle,
        label1NormalMedium = pretendardStyle,
        label1NormalRegular = pretendardStyle,
        label1ReadingBold = pretendardStyle,
        label1ReadingMedium = pretendardStyle,
        label1ReadingRegular = pretendardStyle,
        label2Bold = pretendardStyle,
        label2Medium = pretendardStyle,
        label2Regular = pretendardStyle,
        caption1Bold = pretendardStyle,
        caption1Medium = pretendardStyle,
        caption1Regular = pretendardStyle,
        caption2Bold = pretendardStyle,
        caption2Medium = pretendardStyle,
        caption2Regular = pretendardStyle
    )
}
