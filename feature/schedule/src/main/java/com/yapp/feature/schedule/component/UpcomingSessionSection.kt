package com.yapp.feature.schedule.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.util.formatTimeRange

@Composable
internal fun UpcomingSessionSection(
    id: String,
    title: String,
    date: String,
    dayOfWeek: String,
    location: String?,
    startTime: String?,
    endTime: String?,
    onClick: (String) -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = YappTheme.colorScheme.orange99,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .yappClickable { onClick(id) }
            .padding(12.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = title,
            style = YappTheme.typography.body1ReadingBold,
            color = YappTheme.colorScheme.labelNormal
        )

        Text(
            text = "$date ($dayOfWeek)",
            style = YappTheme.typography.caption1Medium,
            color = YappTheme.colorScheme.labelNeutral
        )

        Spacer(modifier = Modifier.height(12.dp))

        location?.let {
            IconWithText(
                iconResId = com.yapp.core.designsystem.R.drawable.icon_location,
                text = location,
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        val duration = formatTimeRange(
            context = context,
            startTime = startTime,
            endTime = endTime
        )

        duration?.let {
            IconWithText(
                iconResId = com.yapp.core.designsystem.R.drawable.icon_time,
                text = duration,
                contentDescription = null,
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewTodaySessionSection() {
    YappTheme {
        UpcomingSessionSection(
            id = "1234",
            title = "2차 데모데이",
            date = "2023.10.01",
            dayOfWeek = "일요일",
            location = "서울시 강남구",
            startTime = "10:00",
            endTime = "12:00",
            onClick = {}
        )
    }
}