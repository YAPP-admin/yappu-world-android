package com.yapp.feature.schedule.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.designsystem.component.indicator.Indicators
import com.yapp.core.ui.util.formatScheduleTimeRange
import com.yapp.model.AttendanceStatus
import com.yapp.model.ScheduleInfo
import com.yapp.model.ScheduleProgressPhase
import com.yapp.model.ScheduleType
import com.yapp.model.SessionType
import kotlinx.coroutines.launch
import com.yapp.core.designsystem.R as coreDesignR

@Composable
internal fun UpcomingSessionSection(
    modifier: Modifier = Modifier,
    sessions: List<ScheduleInfo>,
) {
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(
        lazyListState = lazyListState,
        snapPosition = SnapPosition.Start
    )
    val selectedIndex by remember {
        derivedStateOf {
            val visibleItems = lazyListState.layoutInfo.visibleItemsInfo
            if (visibleItems.isEmpty()) 0
            else {
                val center = (lazyListState.layoutInfo.viewportStartOffset +
                        lazyListState.layoutInfo.viewportEndOffset) / 2
                visibleItems.minByOrNull {
                    val itemCenter = it.offset + it.size / 2
                    kotlin.math.abs(itemCenter - center)
                }?.index ?: 0
            }
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = lazyListState,
            flingBehavior = flingBehavior,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(
                sessions,
                key = { it.id }
            ) {
                UpcomingSessionItem(
                    title = it.name,
                    date = it.date,
                    endDate = it.endDate,
                    startDayOfWeek = it.startDayOfWeek,
                    endDayOfWeek = it.endDayOfWeek,
                    location = it.place,
                    startTime = it.time,
                    endTime = it.endTime,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Indicators(
            itemCount = sessions.size,
            onPageSelect = { index ->
                scope.launch {
                    lazyListState.animateScrollToItem(index)
                }
            },
            currentPage = selectedIndex,
            activeColor = YappTheme.colorScheme.labelNormal,
            inactiveColor = YappTheme.colorScheme.labelNormal.copy(
                alpha = 0.16f
            ),
        )
    }
}

@Composable
private fun UpcomingSessionItem(
    title: String,
    date: String,
    endDate: String?,
    startDayOfWeek: String,
    endDayOfWeek: String?,
    location: String?,
    startTime: String?,
    endTime: String?,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    Column(
        modifier = Modifier
            .width(configuration.screenWidthDp.dp - 88.dp)
            .background(
                color = YappTheme.colorScheme.orange99,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = title,
            style = YappTheme.typography.body1ReadingBold,
            color = YappTheme.colorScheme.labelNormal
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (!location.isNullOrEmpty()) {
            IconWithText(
                iconResId = coreDesignR.drawable.icon_location,
                text = location,
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        val duration = remember(
            configuration,
            date, startDayOfWeek, startTime, endDate, endDayOfWeek, endTime
        ) {
            formatScheduleTimeRange(
                context = context,
                date = date,
                startDayOfWeek = startDayOfWeek,
                time = startTime,
                endDate = endDate,
                endDayOfWeek = endDayOfWeek,
                endTime = endTime,
            )
        }

        duration?.let {
            IconWithText(
                iconResId = coreDesignR.drawable.icon_time,
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
private fun PreviewUpcomingSessionSection() {
    YappTheme {
        UpcomingSessionSection(
            sessions = listOf(
                ScheduleInfo(
                    id = "1",
                    name = "팀 회의",
                    date = "2023-10-01",
                    endDate = "2023-10-01",
                    place = "회의실 A",
                    time = "10:00",
                    endTime = "11:00",
                    startDayOfWeek = "일",
                    endDayOfWeek = "일",
                    scheduleType = ScheduleType.SESSION,
                    sessionType = SessionType.TEAM,
                    scheduleProgressPhase = ScheduleProgressPhase.ONGOING,
                    attendanceStatus = AttendanceStatus.ATTENDED
                ),
                ScheduleInfo(
                    id = "2",
                    name = "프로젝트 발표",
                    date = "2023-10-02",
                    endDate = "2023-10-02",
                    place = "온라인",
                    time = "14:00",
                    endTime = "15:00",
                    startDayOfWeek = "월",
                    endDayOfWeek = "월",
                    scheduleType = ScheduleType.SESSION,
                    sessionType = SessionType.OFFLINE,
                    scheduleProgressPhase = ScheduleProgressPhase.TODAY,
                    attendanceStatus = AttendanceStatus.EARLY_LEAVE
                )
            )
        )
    }
}