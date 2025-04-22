package com.yapp.feature.schedule.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.model.AttendanceStatus
import com.yapp.model.ScheduleInfo
import com.yapp.model.ScheduleProgressPhase
import com.yapp.model.ScheduleType

@Composable
internal fun DateGroupedScheduleItem(
    date: String,
    dayOfWeek: String,
    scheduleProgressPhase: ScheduleProgressPhase,
    schedules: List<ScheduleInfo>,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { if (scheduleProgressPhase == ScheduleProgressPhase.DONE) alpha = 0.5f }
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
    ) {
        val dateColor = if (scheduleProgressPhase == ScheduleProgressPhase.TODAY) {
            YappTheme.colorScheme.primaryNormal
        } else {
            YappTheme.colorScheme.labelNeutral
        }

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

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            schedules.forEach { schedule ->
                when (schedule.scheduleType) {
                    ScheduleType.SESSION -> {
                        SessionItem(
                            id = schedule.id,
                            title = schedule.name,
                            status = schedule.attendanceStatus,
                            location = schedule.place,
                            time = schedule.time,
                            onClick = onClick,
                        )
                    }

                    ScheduleType.TASK, ScheduleType.ETC -> {
                        AssignmentItem(
                            id = schedule.id,
                            title = schedule.name,
                            content = "",
                            onClick = onClick,
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun DateGroupedScheduleItemPreview() {
    YappTheme {
        DateGroupedScheduleItem(
            date = "5",
            dayOfWeek = "일",
            scheduleProgressPhase = ScheduleProgressPhase.TODAY,
            schedules = listOf(
                ScheduleInfo(
                    id = "1",
                    name = "세션 제목",
                    scheduleType = ScheduleType.SESSION,
                    attendanceStatus = AttendanceStatus.SCHEDULED,
                    place = "공덕 창업허브",
                    time = "오후 2시",
                    endTime = "오후 4시",
                    sessionType = null,
                    scheduleProgressPhase = ScheduleProgressPhase.ONGOING,
                    date = "2023.10.01",
                    endDate = "2023.10.01"
                ),
                ScheduleInfo(
                    id = "2",
                    name = "과제 제목",
                    scheduleType = ScheduleType.TASK,
                    attendanceStatus = AttendanceStatus.SCHEDULED,
                    place = null,
                    time = "오후 2시",
                    endTime = "오후 4시",
                    sessionType = null,
                    scheduleProgressPhase = ScheduleProgressPhase.TODAY,
                    date = "2023.10.01",
                    endDate = "2023.10.01"
                ),
                ScheduleInfo(
                    id = "3",
                    name = "세션 제목",
                    scheduleType = ScheduleType.SESSION,
                    attendanceStatus = AttendanceStatus.SCHEDULED,
                    place = "공덕 창업허브",
                    time = "오후 2시",
                    endTime = "오후 4시",
                    sessionType = null,
                    scheduleProgressPhase = ScheduleProgressPhase.ONGOING,
                    date = "2023.10.01",
                    endDate = "2023.10.01"
                ),
            ),
            onClick = {}
        )
    }
}