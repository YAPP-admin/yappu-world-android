package com.yapp.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                .height(40.dp)
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

@Preview(showBackground = true, widthDp = 800)
@Composable
private fun YappColorPreview() {
    YappTheme {
        val colors = YappTheme.lightColorScheme.let {
            listOf(
                "Primary Normal" to it.primaryNormal,
                "Label Normal" to it.labelNormal,
                "Label Strong" to it.labelStrong,
                "Label Neutral" to it.labelNeutral,
                "Label Alternative" to it.labelAlternative,
                "Label Assistive" to it.labelAssistive,
                "Label Disable" to it.labelDisable,
                "Background Normal Normal" to it.backgroundNormalNormal,
                "Background Normal Alternative" to it.backgroundNormalAlternative,
                "Background Elevated Normal" to it.backgroundElevatedNormal,
                "Background Elevated Alternative" to it.backgroundElevatedAlternative,
                "Interaction Inactive" to it.interactionInactive,
                "Interaction Disable" to it.interactionDisable,
                "Line Normal Normal" to it.lineNormalNormal,
                "Line Normal Neutral" to it.lineNormalNeutral,
                "Line Normal Alternative" to it.lineNormalAlternative,
                "Line Normal Strong" to it.lineNormalStrong,
                "Line Solid Normal" to it.lineSolidNormal,
                "Line Solid Neutral" to it.lineSolidNeutral,
                "Line Solid Alternative" to it.lineSolidAlternative,
                "Line Solid Strong" to it.lineSolidStrong,
                "Fill Normal" to it.fillNormal,
                "Fill Strong" to it.fillStrong,
                "Fill Alternative" to it.fillAlternative,
                "Status Positive" to it.statusPositive,
                "Status Cautionary" to it.statusCautionary,
                "Status Negative" to it.statusNegative,
                "Static White" to it.staticWhite,
                "Static Black" to it.staticBlack,
                "Material Dimmer" to it.materialDimmer
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(colors) { (name, color) ->
                ColorPreview(color = color, name = name)
            }
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
            val typos = YappTheme.typography.let {
                listOf(
                    "Display 1" to it.display1,
                    "Display 2" to it.display2,
                    "Title 1" to it.title1,
                    "Title 2" to it.title2,
                    "Title 3" to it.title3,
                    "Heading 1" to it.heading1,
                    "Heading 2" to it.heading2,
                    "Headline 1" to it.headline1,
                    "Headline 2" to it.headline2,
                    "Body 1 Normal" to it.body1Normal,
                    "Body 1 Reading" to it.body1Reading,
                    "Body 2 Normal" to it.body2Normal,
                    "Body 2 Reading" to it.body2Reading,
                    "Label 1 Normal" to it.label1Normal,
                    "Label 1 Reading" to it.label1Reading,
                    "Label 2" to it.label2,
                    "Caption 1" to it.caption1,
                    "Caption 2" to it.caption2
                )
            }

            typos.forEach { (name, textStyle) ->
                TypographyPreview(
                    name = name,
                    textStyle = textStyle,
                )
            }
        }
    }
}
