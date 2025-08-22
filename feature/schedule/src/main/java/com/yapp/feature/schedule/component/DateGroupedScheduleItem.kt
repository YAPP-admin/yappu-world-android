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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.util.formatScheduleTimeRange
import com.yapp.core.ui.util.formatToDay
import com.yapp.core.ui.util.isPastDate
import com.yapp.model.AttendanceStatus
import com.yapp.model.ScheduleInfo
import com.yapp.model.ScheduleProgressPhase
import com.yapp.model.ScheduleType

@Composable
internal fun DateGroupedScheduleItem(
    date: String,
    dayOfWeek: String,
    isToday: Boolean,
    schedules: List<ScheduleInfo>,
    showMonth: Boolean = false,
    onClick: (String) -> Unit,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val faded = remember(date) { isPastDate(date) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { if (faded) alpha = 0.5f }
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
    ) {
        val dateColor = if (isToday) {
            YappTheme.colorScheme.primaryNormal
        } else {
            YappTheme.colorScheme.labelNeutral
        }
        val dateWidth = if (showMonth) 72.dp else 58.dp

        Row(
            modifier = Modifier.width(dateWidth),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = formatToDay(context, date, showMonth),
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
                        val duration = remember(
                            configuration,
                            schedule.date,
                            schedule.startDayOfWeek,
                            schedule.time,
                            schedule.endDate,
                            schedule.endDayOfWeek,
                            schedule.endTime
                        ) {
                            formatScheduleTimeRange(
                                context = context,
                                date = schedule.date,
                                startDayOfWeek = schedule.startDayOfWeek,
                                time = schedule.time,
                                endDate = schedule.endDate,
                                endDayOfWeek = schedule.endDayOfWeek,
                                endTime = schedule.endTime,
                            )
                        }

                        SessionItem(
                            id = schedule.id,
                            title = schedule.name,
                            attendanceStatus = schedule.attendanceStatus,
                            scheduleProgressPhase = schedule.scheduleProgressPhase,
                            location = schedule.place,
                            duration = duration,
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
        Column {
            DateGroupedScheduleItem(
                date = "2025-05-08",
                dayOfWeek = "일",
                isToday = true,
                schedules = listOf(
                    ScheduleInfo(
                        id = "1",
                        name = "세션 제목",
                        scheduleType = ScheduleType.SESSION,
                        attendanceStatus = null,
                        place = "공덕 창업허브",
                        time = "14:00",
                        endTime = "18:00",
                        startDayOfWeek = "일",
                        endDayOfWeek = "일",
                        sessionType = null,
                        scheduleProgressPhase = ScheduleProgressPhase.ONGOING,
                        date = "2023.10.01",
                        endDate = "2023.10.01"
                    ),
                    ScheduleInfo(
                        id = "2",
                        name = "과제 제목",
                        scheduleType = ScheduleType.TASK,
                        attendanceStatus = AttendanceStatus.ATTENDED,
                        place = null,
                        time = "14:00",
                        endTime = "18:00",
                        startDayOfWeek = "일",
                        endDayOfWeek = "일",
                        sessionType = null,
                        scheduleProgressPhase = ScheduleProgressPhase.TODAY,
                        date = "2023.10.01",
                        endDate = "2023.10.01"
                    ),
                    ScheduleInfo(
                        id = "3",
                        name = "세션 제목",
                        scheduleType = ScheduleType.SESSION,
                        attendanceStatus = AttendanceStatus.EARLY_LEAVE,
                        place = "공덕 창업허브",
                        time = "14:00",
                        endTime = "18:00",
                        startDayOfWeek = "일",
                        endDayOfWeek = "일",
                        sessionType = null,
                        scheduleProgressPhase = ScheduleProgressPhase.ONGOING,
                        date = "2023.10.01",
                        endDate = "2023.10.01"
                    ),
                ),
                showMonth = true,
                onClick = {}
            )

            DateGroupedScheduleItem(
                date = "2025-05-24",
                dayOfWeek = "일",
                isToday = false,
                schedules = listOf(
                    ScheduleInfo(
                        id = "1",
                        name = "세션 제목",
                        scheduleType = ScheduleType.SESSION,
                        attendanceStatus = null,
                        place = "공덕 창업허브",
                        time = "14:00",
                        endTime = "18:00",
                        startDayOfWeek = "일",
                        endDayOfWeek = "일",
                        sessionType = null,
                        scheduleProgressPhase = ScheduleProgressPhase.ONGOING,
                        date = "2023.10.01",
                        endDate = "2023.10.01"
                    ),
                ),
                showMonth = true,
                onClick = {}
            )
        }
    }

}