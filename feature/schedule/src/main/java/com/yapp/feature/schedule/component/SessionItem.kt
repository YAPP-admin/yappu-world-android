package com.yapp.feature.schedule.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.feature.schedule.R

@Composable
internal fun SessionItem(
    id: Long,
    title: String,
    assignmentTitle: String? = null,
    assignmentContent: String? = null,
    status: AttendanceStatus,
    date: String,
    dayOfWeek: String,
    isPast: Boolean = false,
    isToday: Boolean = false,
    location: String,
    time: String,
    onClick: (Long) -> Unit,
) {
    val dateColor = if (isToday) {
        YappTheme.colorScheme.primaryNormal
    } else {
        YappTheme.colorScheme.labelNeutral
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { if (isPast) alpha = 0.5f }
            .yappClickable { onClick(id) }
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = date,
                style = YappTheme.typography.body1NormalBold,
                color = dateColor
            )

            Text(
                text = "(${dayOfWeek})",
                style = YappTheme.typography.caption1Medium,
                color = dateColor
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Column(modifier = Modifier.weight(1f)) {
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = title,
                style = YappTheme.typography.label1NormalBold,
                color = YappTheme.colorScheme.labelNormal
            )

            Spacer(modifier = Modifier.height(6.dp))

            IconWithText(
                iconResId = R.drawable.icon_time,
                text = time,
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(4.dp))

            IconWithText(
                iconResId = R.drawable.icon_location,
                text = location,
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(4.dp))

            if (assignmentTitle != null && assignmentContent != null) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = assignmentTitle,
                    style = YappTheme.typography.label1NormalBold,
                    color = YappTheme.colorScheme.labelNormal
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = assignmentContent,
                    style = YappTheme.typography.caption1Medium,
                    color = YappTheme.colorScheme.labelNeutral
                )
            }
        }

        AttendanceStatusChip(status = status)
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun PreviewSessionItem() {
    YappTheme {
        Column {
            SessionItem(
                id = 1,
                title = "세션 제목",
                status = AttendanceStatus.SCHEDULED,
                date = "12. 6",
                dayOfWeek = "금",
                location = "공덕 창업허브",
                time = "오후 2시 - 오후 6시",
                onClick = {}
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 20.dp)
                    .background(YappTheme.colorScheme.lineNormalAlternative)
            )

            SessionItem(
                id = 2,
                title = "세션 제목",
                assignmentTitle = "과제 제목",
                assignmentContent = "과제 내용",
                status = AttendanceStatus.ATTENDED,
                date = "12. 5",
                dayOfWeek = "금",
                location = "공덕 창업허브",
                time = "오후 2시 - 오후 6시",
                onClick = {}
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 20.dp)
                    .background(YappTheme.colorScheme.lineNormalAlternative)
            )

            SessionItem(
                id = 3,
                title = "세션 제목",
                status = AttendanceStatus.LATE,
                date = "12. 4",
                dayOfWeek = "금",
                location = "공덕 창업허브",
                time = "오후 2시 - 오후 6시",
                onClick = {}
            )
        }
    }
}