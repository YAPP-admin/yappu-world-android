package com.yapp.feature.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.AttendanceStatus
import com.yapp.feature.schedule.component.AssignmentItem
import com.yapp.feature.schedule.component.ScheduleTabRow
import com.yapp.feature.schedule.component.SessionItem
import com.yapp.feature.schedule.component.TodaySessionSection

@Composable
internal fun ScheduleRoute(

) {
    ScheduleScreen()
}

@Composable
internal fun ScheduleScreen(

) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YappTheme.colorScheme.staticWhite)
    ) {
        ScheduleHeader()
        Spacer(modifier = Modifier.height(6.dp))

        ScheduleTabRow(
            selectedTabIndex = selectedTabIndex,
            tabList = listOf(
                stringResource(id = R.string.schedule_tab_all),
                stringResource(id = R.string.schedule_tab_session)
            ),
            onTabSelected = { selectedTabIndex = it }
        )

        when (selectedTabIndex) {
            0 -> ScheduleAllScreen()
            1 -> ScheduleSessionScreen()
        }
    }
}

@Composable
private fun ScheduleAllScreen() {
    LazyColumn {
        item {
            Spacer(modifier = Modifier.height(20.dp))
            MonthHeader(
                modifier = Modifier.padding(horizontal = 20.dp),
                year = 2024,
                month = 12,
                onPreviousMonthClick = {},
                onNextMonthClick = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        val statuses = listOf(
            AttendanceStatus.SCHEDULED,
            AttendanceStatus.ATTENDED,
            AttendanceStatus.LATE
        )

        for (index in 0..6) {
            item {
                SessionItem(
                    id = index.toLong(),
                    title = "세션 제목",
                    assignmentTitle = if (index == 1) "과제 제목" else null,
                    assignmentContent = if (index == 1) "과제 내용" else null,
                    status = statuses[index % statuses.size],
                    date = "${9 - index}",
                    dayOfWeek = "금",
                    isToday = index == 0,
                    isPast = index > 0,
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
            }
        }

        item {
            AssignmentItem(
                id = 0,
                title = "과제 제목",
                content = "과제 내용",
                date = "2",
                dayOfWeek = "금",
                isToday = false,
                isPast = true,
                onClick = {}
            )
        }
    }
}

@Composable
private fun ScheduleSessionScreen(
    hasTodaySession: Boolean = true,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.today_session_section_title),
                    style = YappTheme.typography.headline2Bold,
                    color = YappTheme.colorScheme.labelNormal
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (hasTodaySession) {
                    TodaySessionSection(
                        id = 0,
                        title = "오늘의 세션",
                        date = "2024.12.25",
                        dayOfWeek = "화요일",
                        location = "서울시 강남구",
                        time = "오후 2:00 - 오후 3:00",
                        remainingDays = 2,
                        onClick = {}
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        text = stringResource(id = R.string.today_session_empty_message),
                        style = YappTheme.typography.label2Regular,
                        color = YappTheme.colorScheme.labelAlternative,
                        textAlign = TextAlign.Center
                    )
                }

            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .background(YappTheme.colorScheme.lineNormalAlternative)
            )
        }

        val statuses = listOf(
            AttendanceStatus.SCHEDULED,
            AttendanceStatus.ATTENDED,
            AttendanceStatus.LATE
        )

        for (index in 0..7) {
            item {
                SessionItem(
                    id = index.toLong(),
                    title = "세션 제목",
                    status = statuses[index % statuses.size],
                    date = "12. ${9 - index}",
                    dayOfWeek = "금",
                    isToday = index == 0,
                    isPast = index > 0,
                    location = "공덕 창업허브",
                    time = "오후 2시 - 오후 6시",
                    onClick = {}
                )

                if (index != 7) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .padding(horizontal = 20.dp)
                            .background(YappTheme.colorScheme.lineNormalAlternative)
                    )
                }
            }
        }
    }
}

@Composable
private fun ScheduleHeader(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 12.dp
            )
    ) {
        Text(
            text = stringResource(id = R.string.schedule_screen_title),
            style = YappTheme.typography.heading1Bold,
            color = YappTheme.colorScheme.labelNormal,
        )
    }
}

@Composable
private fun MonthHeader(
    modifier: Modifier = Modifier,
    year: Int,
    month: Int,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .yappClickable(
                    rippleBounded = false,
                    rippleRadius = 24.dp
                ) {
                    onPreviousMonthClick()
                },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(id = com.yapp.core.designsystem.R.drawable.icon_chevron_left),
                contentDescription = null,
                tint = YappTheme.colorScheme.labelAssistive
            )
        }

        Text(
            text = "${year}년 ${month}월",
            style = YappTheme.typography.headline1Bold,
            color = YappTheme.colorScheme.labelNormal,
        )

        Icon(
            modifier = Modifier.yappClickable(
                rippleBounded = false,
                rippleRadius = 24.dp
            ) {
                onNextMonthClick()
            },
            painter = painterResource(id = com.yapp.core.designsystem.R.drawable.icon_chevron_right),
            contentDescription = null,
            tint = YappTheme.colorScheme.labelAssistive
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScheduleScreenPreview() {
    YappTheme {
        ScheduleRoute()
    }
}