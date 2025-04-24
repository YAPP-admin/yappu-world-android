package com.yapp.feature.history.attend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Composable
internal fun AttendanceStatusSection(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    totalRate: String,
    slot: @Composable RowScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = YappTheme.colorScheme.backgroundNormalAlternative)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(YappTheme.colorScheme.labelDisable)
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    style = YappTheme.typography.label1NormalBold,
                    text = title
                )
                Text(style = YappTheme.typography.label2Medium, text = subTitle)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    style = YappTheme.typography.body2NormalBold,
                    text = totalRate,
                    color = YappTheme.colorScheme.primaryNormal
                )
            }
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .padding(vertical = 10.dp),
                content = slot
            )
        }
    }
}

@Preview
@Composable
private fun AttendanceStatusPreview() {
    YappTheme {
        Column {
            AttendanceStatusSection(
                title = "내 출석 현황",
                subTitle = "총 점수",
                totalRate = "98점",
                slot = {
                    StatusItem(modifier = Modifier.weight(1f), title = "출석", count = 12)
                    StatusItem(modifier = Modifier.weight(1f), title = "지각", count = 5)
                    StatusItem(modifier = Modifier.weight(1f), title = "결석", count = 0)
                    StatusItem(modifier = Modifier.weight(1f), title = "지각 면제권", count = 12)
                },
            )

            Spacer(modifier = Modifier.height(50.dp))

            AttendanceStatusSection(
                title = "세션 현황",
                subTitle = "진행률",
                totalRate = "50%",
                slot = {
                    StatusItem(modifier = Modifier.weight(1f), title = "전체 세션", count = 12)
                    StatusItem(modifier = Modifier.weight(1f), title = "남은 세션", count = 5)
                },
            )
        }
    }
}
