package com.yapp.feature.schedule.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.R
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.ScheduleStatusChip
import com.yapp.model.AttendanceStatus
import com.yapp.model.ScheduleProgressPhase

@Composable
internal fun SessionItem(
    id: String,
    title: String,
    attendanceStatus: AttendanceStatus?,
    scheduleProgressPhase: ScheduleProgressPhase,
    location: String?,
    duration: String?,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .yappClickable { onClick(id) }
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = title,
                style = YappTheme.typography.label1NormalBold,
                color = YappTheme.colorScheme.labelNormal
            )

            Spacer(modifier = Modifier.height(6.dp))

            duration?.let {
                IconWithText(
                    iconResId = R.drawable.icon_time,
                    text = duration,
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            location?.let {
                IconWithText(
                    iconResId = R.drawable.icon_location,
                    text = location,
                    contentDescription = null,
                )
            }
        }

        ScheduleStatusChip(
            attendanceStatus = attendanceStatus,
            scheduleProgressPhase = scheduleProgressPhase,
        )
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun PreviewSessionItem() {
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SessionItem(
                id = "1",
                title = "세션 제목",
                attendanceStatus = AttendanceStatus.LATE,
                scheduleProgressPhase = ScheduleProgressPhase.DONE,
                location = "공덕 창업허브",
                duration = "오후 2시 - 오후 6시",
                onClick = {}
            )

            SessionItem(
                id = "2",
                title = "세션 제목",
                attendanceStatus = AttendanceStatus.ATTENDED,
                scheduleProgressPhase = ScheduleProgressPhase.DONE,
                location = "공덕 창업허브",
                duration = "오후 2시 - 오후 6시",
                onClick = {}
            )

            SessionItem(
                id = "3",
                title = "세션 제목",
                attendanceStatus = AttendanceStatus.EARLY_LEAVE,
                scheduleProgressPhase = ScheduleProgressPhase.DONE,
                location = "공덕 창업허브",
                duration = "오후 2시 - 오후 6시",
                onClick = {}
            )
        }
    }
}