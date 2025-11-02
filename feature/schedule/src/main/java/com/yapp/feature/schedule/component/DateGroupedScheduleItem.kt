package com.yapp.feature.schedule.component

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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

enum class ScheduleGroupVariant {
    LEFT_ALIGNED, TOP_ALIGNED
}

@Composable
internal fun DateGroupedScheduleItem(
    variant: ScheduleGroupVariant,
    date: String,
    dayOfWeek: String,
    isToday: Boolean,
    schedules: List<ScheduleInfo>,
    showMonth: Boolean = false,
    onClick: (String) -> Unit,
) {
    val today = remember { LocalDate.now() }
    val parsedDate = remember(date) {
        runCatching { LocalDate.parse(date, DateTimeFormatter.ISO_DATE) }.getOrNull()
    }

    val dateState = remember(parsedDate, today) {
        when {
            parsedDate == null -> SessionDateState.FUTURE
            parsedDate.isEqual(today) -> SessionDateState.TODAY
            parsedDate.isBefore(today) -> SessionDateState.PAST
            else -> SessionDateState.FUTURE
        }
    }

    val faded = remember(date) { isPastDate(date) }

    when (variant) {
        ScheduleGroupVariant.LEFT_ALIGNED -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer { if (faded) alpha = 0.5f }
                    .padding(horizontal = 20.dp)
            ) {
                DateHeader(
                    date = date,
                    dayOfWeek = dayOfWeek,
                    isToday = isToday,
                    showMonth = showMonth
                )
                Spacer(modifier = Modifier.width(8.dp))
                ScheduleList(
                    schedules = schedules,
                    dateState = dateState,
                    onClick = onClick
                )
            }
        }

        ScheduleGroupVariant.TOP_ALIGNED -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer { if (faded) alpha = 0.5f }
                    .padding(horizontal = 20.dp),
            ) {
                DateHeader(
                    date = date,
                    dayOfWeek = dayOfWeek,
                    isToday = isToday,
                    showMonth = showMonth
                )
                Spacer(modifier = Modifier.height(8.dp))
                ScheduleList(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    schedules = schedules,
                    dateState = dateState,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
private fun DateHeader(
    date: String,
    dayOfWeek: String,
    isToday: Boolean,
    showMonth: Boolean,
) {
    val context = LocalContext.current

    val dateColor = if (isToday) YappTheme.colorScheme.primaryNormal else YappTheme.colorScheme.labelNeutral
    val dateWidth = if (showMonth) 72.dp else 60.dp

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
}

@Composable
private fun ScheduleList(
    modifier: Modifier = Modifier,
    schedules: List<ScheduleInfo>,
    dateState: SessionDateState,
    onClick: (String) -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        schedules.forEach { schedule ->
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

            when (schedule.scheduleType) {
                ScheduleType.SESSION -> SessionItem(
                    id = schedule.id,
                    title = schedule.name,
                    attendanceStatus = schedule.attendanceStatus,
                    scheduleProgressPhase = schedule.scheduleProgressPhase,
                    location = schedule.place,
                    duration = duration,
                    dateState = dateState,
                    onClick = onClick
                )

                ScheduleType.TASK, ScheduleType.ETC -> AssignmentItem(
                    id = schedule.id,
                    title = schedule.name,
                    content = "",
                    dateState = dateState,
                    onClick = onClick
                )
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
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DateGroupedScheduleItem(
                date = "2025-11-02",
                variant = ScheduleGroupVariant.LEFT_ALIGNED,
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
                        date = "2023-10-01",
                        endDate = "2023-10-01"
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
                        date = "2023-10-01",
                        endDate = "2023-10-01"
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
                        date = "2023-10-01",
                        endDate = "2023-11-01"
                    ),
                ),
                showMonth = false,
                onClick = {}
            )

            DateGroupedScheduleItem(
                date = "2025-12-02",
                variant = ScheduleGroupVariant.LEFT_ALIGNED,
                dayOfWeek = "일",
                isToday = false,
                schedules = listOf(
                    ScheduleInfo(
                        id = "1",
                        name = "세션 제목",
                        scheduleType = ScheduleType.SESSION,
                        attendanceStatus = AttendanceStatus.ATTENDED,
                        place = "공덕 창업허브",
                        time = "14:00",
                        endTime = "18:00",
                        startDayOfWeek = "일",
                        endDayOfWeek = "일",
                        sessionType = null,
                        scheduleProgressPhase = ScheduleProgressPhase.ONGOING,
                        date = "2023-10-01",
                        endDate = "2023-10-01"
                    ),
                ),
                showMonth = false,
                onClick = {}
            )

            DateGroupedScheduleItem(
                date = "2025-11-02",
                variant = ScheduleGroupVariant.TOP_ALIGNED,
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
                        date = "2023-10-01",
                        endDate = "2023-10-01"
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
                        date = "2023-10-01",
                        endDate = "2023-10-01"
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
                        date = "2023-10-01",
                        endDate = "2023-11-01"
                    ),
                ),
                showMonth = true,
                onClick = {}
            )

            DateGroupedScheduleItem(
                date = "2025-05-24",
                variant = ScheduleGroupVariant.TOP_ALIGNED,
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
                        date = "2023-10-01",
                        endDate = "2023-10-01"
                    ),
                ),
                showMonth = true,
                onClick = {}
            )
        }
    }

}