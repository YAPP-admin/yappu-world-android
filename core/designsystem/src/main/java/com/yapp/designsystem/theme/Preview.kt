package com.yapp.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
private fun ColorPreview(color: Color, name: String) {
    Column {
        BasicText(
            text = name,
            style = TextStyle(color = YappTheme.lightColorScheme.staticBlack, fontSize = 14.sp)
        )
        Spacer(Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(color)
                .border(width = 1.dp, color = YappTheme.lightColorScheme.lineSolidNormal)
        )
        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun TypographyPreview(textStyle: TextStyle, name: String) {
    BasicText(
        text = name,
        modifier = Modifier.padding(8.dp),
        style = textStyle
    )
}

@Preview(showBackground = true)
@Composable
private fun YappColorPreview() {
    YappTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ColorPreview(
                color = YappTheme.lightColorScheme.primaryNormal,
                name = "Primary Normal"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.labelNormal,
                name = "Label Normal"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.labelStrong,
                name = "Label Strong"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.labelNeutral,
                name = "Label Neutral"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.labelAlternative,
                name = "Label Alternative"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.labelAssistive,
                name = "Label Assistive"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.labelDisable,
                name = "Label Disable"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.backgroundNormalNormal,
                name = "Background Normal Normal"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.backgroundNormalAlternative,
                name = "Background Normal Alternative"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.backgroundElevatedNormal,
                name = "Background Elevated Normal"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.backgroundElevatedAlternative,
                name = "Background Elevated Alternative"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.interactionInactive,
                name = "Interaction Inactive"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.interactionDisable,
                name = "Interaction Disable"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.lineNormalNormal,
                name = "Line Normal Normal"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.lineNormalNeutral,
                name = "Line Normal Neutral"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.lineNormalAlternative,
                name = "Line Normal Alternative"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.lineSolidNormal,
                name = "Line Solid Normal"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.lineSolidNeutral,
                name = "Line Solid Neutral"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.lineSolidAlternative,
                name = "Line Solid Alternative"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.fillNormal,
                name = "Fill Normal"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.fillStrong,
                name = "Fill Strong"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.fillAlternative,
                name = "Fill Alternative"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.statusPositive,
                name = "Status Positive"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.statusCautionary,
                name = "Status Cautionary"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.statusNegative,
                name = "Status Negative"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.staticWhite,
                name = "Static White"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.staticBlack,
                name = "Static Black"
            )
            ColorPreview(
                color = YappTheme.lightColorScheme.materialDimmer,
                name = "Material Dimmer"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun YappTypographyPreview() {
    YappTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TypographyPreview(
                textStyle = YappTheme.typography.display1,
                name = "Display 1"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.display2,
                name = "Display 2"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.title1,
                name = "Title 1"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.title2,
                name = "Title 2"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.title3,
                name = "Title 3"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.heading1,
                name = "Heading 1"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.heading2,
                name = "Heading 2"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.headline1,
                name = "Headline 1"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.headline2,
                name = "Headline 2"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.body1Normal,
                name = "Body 1 Normal"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.body1Reading,
                name = "Body 1 Reading"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.body2Normal,
                name = "Body 2 Normal"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.body2Reading,
                name = "Body 2 Reading"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.label1Normal,
                name = "Label 1 Normal"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.label1Reading,
                name = "Label 1 Reading"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.label2,
                name = "Label 2"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.caption1,
                name = "Caption 1"
            )
            TypographyPreview(
                textStyle = YappTheme.typography.caption2,
                name = "Caption 2"
            )
        }
    }
}
