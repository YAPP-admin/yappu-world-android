package com.yapp.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.designsystem.R as coreDesignR

@Composable
internal fun FAQSection(
    modifier: Modifier = Modifier,
    clickBasicRule: () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "혹시... 처음이 YAPP?", style = YappTheme.typography.headline1Bold)
        Spacer(Modifier.height(8.dp))
        Faq(clickBasicRule = clickBasicRule)
    }
}

@Preview(showBackground = true)
@Composable
private fun FaQSectionPreview() {
    YappTheme { FAQSection(clickBasicRule = {}) }
}

@Composable
private fun Faq(
    clickBasicRule: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color = YappTheme.colorScheme.staticWhite, shape = RoundedCornerShape(16.dp))
            .yappClickable(onClick = { clickBasicRule() })
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Image(painter = painterResource(coreDesignR.drawable.yappo_wink), contentDescription = null)
            Text(text = "YAPP 기본 규칙", style = YappTheme.typography.body1NormalRegular)
        }
        Icon(
            painter = painterResource(coreDesignR.drawable.icon_chevron_right),
            contentDescription = null,
            tint = YappTheme.colorScheme.labelDisable
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FaqPreview() {
    YappTheme { Faq(clickBasicRule = {}) }
}
