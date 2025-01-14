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
            style = TextStyle(color = YappTheme.colorScheme.staticBlack, fontSize = 14.sp)
        )
        Spacer(Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(color)
                .border(width = 1.dp, color = YappTheme.colorScheme.lineSolidNormal)
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
        val colors = YappTheme.colorScheme.let {
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

@Preview(showBackground = true, widthDp = 800, heightDp = 1000)
@Composable
private fun YappTypographyPreview() {
    YappTheme {
        val typos = YappTheme.typography.let {
            listOf(
                "Display 1 Bold" to it.display1Bold,
                "Display 1 Medium" to it.display1Medium,
                "Display 1 Regular" to it.display1Regular,
                "Display 2 Bold" to it.display2Bold,
                "Display 2 Medium" to it.display2Medium,
                "Display 2 Regular" to it.display2Regular,
                "Title 1 Bold" to it.title1Bold,
                "Title 1 Medium" to it.title1Medium,
                "Title 1 Regular" to it.title1Regular,
                "Title 2 Bold" to it.title2Bold,
                "Title 2 Medium" to it.title2Medium,
                "Title 2 Regular" to it.title2Regular,
                "Title 3 Bold" to it.title3Bold,
                "Title 3 Medium" to it.title3Medium,
                "Title 3 Regular" to it.title3Regular,
                "Heading 1 Bold" to it.heading1Bold,
                "Heading 1 Medium" to it.heading1Medium,
                "Heading 1 Regular" to it.heading1Regular,
                "Heading 2 Bold" to it.heading2Bold,
                "Heading 2 Medium" to it.heading2Medium,
                "Heading 2 Regular" to it.heading2Regular,
                "Headline 1 Bold" to it.headline1Bold,
                "Headline 1 Medium" to it.headline1Medium,
                "Headline 1 Regular" to it.headline1Regular,
                "Headline 2 Bold" to it.headline2Bold,
                "Headline 2 Medium" to it.headline2Medium,
                "Headline 2 Regular" to it.headline2Regular,
                "Body 1 Normal Bold" to it.body1NormalBold,
                "Body 1 Normal Medium" to it.body1NormalMedium,
                "Body 1 Normal Regular" to it.body1NormalRegular,
                "Body 1 Reading Bold" to it.body1ReadingBold,
                "Body 1 Reading Medium" to it.body1ReadingMedium,
                "Body 1 Reading Regular" to it.body1ReadingRegular,
                "Body 2 Normal Bold" to it.body2NormalBold,
                "Body 2 Normal Medium" to it.body2NormalMedium,
                "Body 2 Normal Regular" to it.body2NormalRegular,
                "Body 2 Reading Bold" to it.body2ReadingBold,
                "Body 2 Reading Medium" to it.body2ReadingMedium,
                "Body 2 Reading Regular" to it.body2ReadingRegular,
                "Label 1 Normal Bold" to it.label1NormalBold,
                "Label 1 Normal Medium" to it.label1NormalMedium,
                "Label 1 Normal Regular" to it.label1NormalRegular,
                "Label 1 Reading Bold" to it.label1ReadingBold,
                "Label 1 Reading Medium" to it.label1ReadingMedium,
                "Label 1 Reading Regular" to it.label1ReadingRegular,
                "Label 2 Bold" to it.label2Bold,
                "Label 2 Medium" to it.label2Medium,
                "Label 2 Regular" to it.label2Regular,
                "Caption 1 Bold" to it.caption1Bold,
                "Caption 1 Medium" to it.caption1Medium,
                "Caption 1 Regular" to it.caption1Regular,
                "Caption 2 Bold" to it.caption2Bold,
                "Caption 2 Medium" to it.caption2Medium,
                "Caption 2 Regular" to it.caption2Regular
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(typos) { (name, textStyle) ->
                TypographyPreview(
                    name = name,
                    textStyle = textStyle,
                )
            }
        }

    }
}
