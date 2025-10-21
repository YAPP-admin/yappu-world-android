package com.yapp.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    clickCurriculum: () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "혹시... 처음이 YAPP?", style = YappTheme.typography.headline1Bold)
        Spacer(Modifier.height(8.dp))
        Faq(
            clickBasicRule = clickBasicRule,
            clickCurriculum = clickCurriculum
        )
    }
}

@Preview
@Composable
private fun FaQSectionPreview() {
    YappTheme { FAQSection(clickCurriculum = {}, clickBasicRule = {}) }
}

@Composable
private fun Faq(
    clickBasicRule: () -> Unit,
    clickCurriculum: () -> Unit,
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color = YappTheme.colorScheme.staticWhite)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth()
                .yappClickable(onClick = { clickBasicRule() })
        ) {
            Image(painter = painterResource(coreDesignR.drawable.yappo_wink), contentDescription = null)
            Text(text = "YAPP 기본 규칙")
        }
        HorizontalDivider(color = YappTheme.colorScheme.lineNormalAlternative)
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth()
                .yappClickable(onClick = { clickCurriculum() })
        ) {
            Image(painter = painterResource(coreDesignR.drawable.yappo_smile), contentDescription = null)
            Text(text = "N기 커리큘럼")
        }
    }
}

@Preview
@Composable
private fun FaqPreview() {
    YappTheme { Faq(clickCurriculum = {}, clickBasicRule = {}) }
}
