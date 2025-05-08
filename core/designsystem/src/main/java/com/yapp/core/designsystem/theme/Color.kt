package com.yapp.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

internal val LightColorScheme = YappColorScheme(
    primaryNormal = Color(0xFFFA6027),
    secondaryNormal = Color(0xFFFFAD31),

    labelNormal = Color(0xFF171719),
    labelStrong = Color(0xFF000000),
    labelNeutral = Color(0xE02E2F33),
    labelAlternative = Color(0x9C37383C),
    labelAssistive = Color(0x4737383C),
    labelDisable = Color(0x2937383C),

    backgroundNormalNormal = Color(0xFFFFFFFF),
    backgroundNormalAlternative = Color(0xFFF7F7F8),
    backgroundElevatedNormal = Color(0xFFFFFFFF),
    backgroundElevatedAlternative = Color(0xFFF7F7F8),

    interactionInactive = Color(0xFF989BA2),
    interactionDisable = Color(0xFFF4F4F5),

    lineNormalNormal = Color(0x3870737C),
    lineNormalNeutral = Color(0x2970737C),
    lineNormalAlternative = Color(0x1470737C),
    lineNormalStrong = Color(0x70737C85),
    lineSolidNormal = Color(0xFFE1E2E4),
    lineSolidNeutral = Color(0xFFEAEBEC),
    lineSolidAlternative = Color(0xFFF4F4F5),
    lineSolidStrong = Color(0xFFAEB0B6),

    fillNormal = Color(0x1470737C),
    fillStrong = Color(0x2970737C),
    fillAlternative = Color(0x0D70737C),

    statusPositive = Color(0xFF00BF40),
    statusCautionary = Color(0xFFFF9200),
    statusNegative = Color(0xFFFF4242),

    staticWhite = Color(0xFFFFFFFF),
    staticBlack = Color(0xFF000000),

    accentLightBlue = Color(0xFF00AEFF),
    accentLightBlueWeak = Color(0xFFE5F7FF),

    accentRed = Color(0xFFE32908),
    accentRedWeak = Color(0xFFFEE6E1),

    accentViolet = Color(0xFF6541F2),
    accentVioletWeak = Color(0xFFECE7FD),

    coolNeutral50 = Color(0xFF70737C),
    neutral40 = Color(0xFF5C5C5C),
    neutral95 = Color(0xFFDCDCDC),
    orange99 = Color(0xFFFFF8F5),
    yellow95 = Color(0xFFFFF7EA),

    materialDimmer = Color(0x85171719),

    semanticFillString = Color(0x2970737C),
    semanticFillAlternative = Color(0xFFDFDFDF),

    skeleton = Brush.linearGradient(
        colors = listOf(
            Color(0xFFEDEDED),
            Color(0x0DDBDBDB)
        ),
        start = Offset(0f, 0f),
        end = Offset(1000f, 0f)
    )
)

@Immutable
data class YappColorScheme(
    val primaryNormal: Color,
    val secondaryNormal: Color,

    val labelNormal: Color,
    val labelStrong: Color,
    val labelNeutral: Color,
    val labelAlternative: Color,
    val labelAssistive: Color,
    val labelDisable: Color,

    val backgroundNormalNormal: Color,
    val backgroundNormalAlternative: Color,
    val backgroundElevatedNormal: Color,
    val backgroundElevatedAlternative: Color,

    val interactionInactive: Color,
    val interactionDisable: Color,

    val lineNormalNormal: Color,
    val lineNormalNeutral: Color,
    val lineNormalAlternative: Color,
    val lineNormalStrong: Color,
    val lineSolidNormal: Color,
    val lineSolidNeutral: Color,
    val lineSolidAlternative: Color,
    val lineSolidStrong: Color,

    val fillNormal: Color,
    val fillStrong: Color,
    val fillAlternative: Color,

    val statusPositive: Color,
    val statusCautionary: Color,
    val statusNegative: Color,

    val staticWhite: Color,
    val staticBlack: Color,

    val accentLightBlue: Color,
    val accentLightBlueWeak: Color,

    val accentRed: Color,
    val accentRedWeak: Color,

    val accentViolet: Color,
    val accentVioletWeak: Color,

    val coolNeutral50: Color,
    val neutral40: Color,
    val neutral95: Color,
    val orange99: Color,
    val yellow95: Color,

    val materialDimmer: Color,

    val semanticFillString: Color,
    val semanticFillAlternative: Color,

    val skeleton: Brush,

    )

val LocalColorScheme = staticCompositionLocalOf {
    YappColorScheme(
        primaryNormal = Color.Unspecified,
        secondaryNormal = Color.Unspecified,
        labelNormal = Color.Unspecified,
        labelStrong = Color.Unspecified,
        labelNeutral = Color.Unspecified,
        labelAlternative = Color.Unspecified,
        labelAssistive = Color.Unspecified,
        labelDisable = Color.Unspecified,
        backgroundNormalNormal = Color.Unspecified,
        backgroundNormalAlternative = Color.Unspecified,
        backgroundElevatedNormal = Color.Unspecified,
        backgroundElevatedAlternative = Color.Unspecified,
        interactionInactive = Color.Unspecified,
        interactionDisable = Color.Unspecified,
        lineNormalNormal = Color.Unspecified,
        lineNormalNeutral = Color.Unspecified,
        lineNormalAlternative = Color.Unspecified,
        lineNormalStrong = Color.Unspecified,
        lineSolidNormal = Color.Unspecified,
        lineSolidNeutral = Color.Unspecified,
        lineSolidAlternative = Color.Unspecified,
        lineSolidStrong = Color.Unspecified,
        fillNormal = Color.Unspecified,
        fillStrong = Color.Unspecified,
        fillAlternative = Color.Unspecified,
        statusPositive = Color.Unspecified,
        statusCautionary = Color.Unspecified,
        statusNegative = Color.Unspecified,
        staticWhite = Color.Unspecified,
        staticBlack = Color.Unspecified,
        accentLightBlue = Color.Unspecified,
        accentLightBlueWeak = Color.Unspecified,
        accentRed = Color.Unspecified,
        accentRedWeak = Color.Unspecified,
        accentViolet = Color.Unspecified,
        accentVioletWeak = Color.Unspecified,
        coolNeutral50 = Color.Unspecified,
        neutral40 = Color.Unspecified,
        neutral95 = Color.Unspecified,
        orange99 = Color.Unspecified,
        yellow95 = Color.Unspecified,
        materialDimmer = Color.Unspecified,
        semanticFillString = Color.Unspecified,
        semanticFillAlternative = Color.Unspecified,
        skeleton = Brush.linearGradient(
            colors = listOf(Color.Unspecified, Color.Unspecified)
        )
    )
}