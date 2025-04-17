package com.yapp.feature.schedule.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.chip.ChipColorType
import com.yapp.core.designsystem.component.chip.YappChipSmall
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.extension.dashedBorder

@Composable
internal fun TodaySessionSection(
    id: Long,
    title: String,
    date: String,
    dayOfWeek: String,
    location: String,
    time: String,
    remainingDays: Int,
    onClick: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .yappClickable { onClick(id) }
            .dashedBorder(
                color = Color(0xFFFED9CB),
                dashLength = 2.dp,
                gapLength = 2.dp,
                cornerRadius = 10.dp,
            )
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = title,
                style = YappTheme.typography.body1ReadingBold,
                color = YappTheme.colorScheme.labelNormal
            )

            YappChipSmall(
                text = if (remainingDays > 0) {
                    "D-${remainingDays}"
                } else {
                    "D-day"
                },
                colorType = ChipColorType.Main,
                isFill = true
            )
        }

        Text(
            text = "$date ($dayOfWeek)",
            style = YappTheme.typography.caption1Medium,
            color = YappTheme.colorScheme.labelNeutral
        )

        Spacer(modifier = Modifier.height(12.dp))

        IconWithText(
            iconResId = com.yapp.core.designsystem.R.drawable.icon_location,
            text = location,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(4.dp))

        IconWithText(
            iconResId = com.yapp.core.designsystem.R.drawable.icon_time,
            text = time,
            contentDescription = null,
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewTodaySessionSection() {
    YappTheme {
        TodaySessionSection(
            id = 1L,
            title = "2차 데모데이",
            date = "2023.10.01",
            dayOfWeek = "일요일",
            location = "서울시 강남구",
            time = "오후 2시 - 오후 4시",
            remainingDays = 3,
            onClick = {}
        )
    }
}