package com.yapp.feature.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.feature.schedule.component.AttendanceStatus
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

        if (selectedTabIndex == 0) {
            ScheduleAllScreen()
        } else {
            ScheduleSessionScreen()
        }
    }
}

@Composable
private fun ScheduleAllScreen() {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
            MonthHeader(
                year = 2024,
                month = 12,
                onPreviousMonthClick = {},
                onNextMonthClick = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
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

        statuses.forEachIndexed { index, status ->
            item {
                SessionItem(
                    id = index.toLong(),
                    title = "세션 제목",
                    status = status,
                    date = "12. ${6 - index}",
                    dayOfWeek = "금",
                    location = "공덕 창업허브",
                    time = "오후 2시 - 오후 6시",
                    onClick = {}
                )

                if (index != statuses.lastIndex) {
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
    year: Int,
    month: Int,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.clickable { onPreviousMonthClick() },
            painter = painterResource(id = R.drawable.icon_arrow_left),
            contentDescription = null,
            tint = Color.Unspecified
        )

        Text(
            text = "${year}년 ${month}월",
            style = YappTheme.typography.headline1Bold,
            color = YappTheme.colorScheme.labelNormal,
        )

        Icon(
            modifier = Modifier.clickable { onNextMonthClick() },
            painter = painterResource(id = R.drawable.icon_arrow_right),
            contentDescription = null,
            tint = Color.Unspecified
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