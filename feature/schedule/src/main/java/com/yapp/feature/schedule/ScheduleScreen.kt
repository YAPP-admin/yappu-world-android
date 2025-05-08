package com.yapp.feature.schedule

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.chip.ChipColorType
import com.yapp.core.designsystem.component.chip.YappChipSmall
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.schedule.component.DateGroupedScheduleItem
import com.yapp.feature.schedule.component.ScheduleTabRow
import com.yapp.feature.schedule.component.UpcomingSessionSection
import com.yapp.model.ScheduleList
import com.yapp.core.ui.R as coreR
import com.yapp.model.UpcomingSessionInfo

@Composable
internal fun ScheduleRoute(
    viewModel: ScheduleViewModel = hiltViewModel(),
    handleException: (Throwable) -> Unit,
    navigateToLogin: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.store.onIntent(ScheduleIntent.EnterScheduleScreen)
    }

    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    viewModel.store.sideEffects.collectWithLifecycle { effect ->
        when (effect) {
            is ScheduleSideEffect.HandleException -> handleException(effect.exception)
            ScheduleSideEffect.NavigateToLogin -> navigateToLogin()
        }
    }

    ScheduleScreen(
        scheduleState = uiState,
        onIntent = { viewModel.store.onIntent(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScheduleScreen(
    scheduleState: ScheduleState,
    onIntent: (ScheduleIntent) -> Unit = {},
) {
    val pullToRefreshState = rememberPullToRefreshState()

    YappBackground(
        color = YappTheme.colorScheme.staticWhite,
    ) {
        PullToRefreshBox(
            isRefreshing = scheduleState.isLoading,
            state = pullToRefreshState,
            onRefresh = { onIntent(ScheduleIntent.RefreshTab(scheduleState.selectedTab)) },
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = scheduleState.isLoading,
                    state = pullToRefreshState,
                    containerColor = YappTheme.colorScheme.staticWhite
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                ScheduleHeader()
                Spacer(modifier = Modifier.height(6.dp))

                ScheduleTabRow(
                    selectedTab = scheduleState.selectedTab,
                    tabList = ScheduleTab.entries,
                    onTabSelected = {
                        onIntent(ScheduleIntent.SelectTab(it))
                    }
                )

                when (scheduleState.selectedTab) {
                    ScheduleTab.ALL -> {
                        ScheduleAllScreen(
                            selectedYear = scheduleState.selectedYear,
                            selectedMonth = scheduleState.selectedMonth,
                            schedules = scheduleState.schedules[
                                Pair(scheduleState.selectedYear, scheduleState.selectedMonth)
                            ],
                            onIntent = onIntent
                        )
                    }

                    ScheduleTab.SESSION -> ScheduleSessionScreen(
                        upcomingSessionInfo = scheduleState.upcomingSessionInfo,
                        sessions = scheduleState.sessions
                    )
                }
            }
        }
    }
}

@Composable
private fun ScheduleAllScreen(
    selectedYear: Int,
    selectedMonth: Int,
    schedules: ScheduleList?,
    onIntent: (ScheduleIntent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
            MonthHeader(
                modifier = Modifier.padding(horizontal = 20.dp),
                year = selectedYear,
                month = selectedMonth,
                onPreviousMonthClick = { onIntent(ScheduleIntent.ClickPreviousMonth) },
                onNextMonthClick = { onIntent(ScheduleIntent.ClickNextMonth) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (schedules?.isEmpty == true) {
            item {
                Column(
                    modifier = Modifier
                        .fillParentMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = coreR.drawable.illust_yappu_sleeping),
                        contentDescription = null,
                    )
                    Spacer(Modifier.height(32.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "등록된 일정이 없습니다.",
                        color = YappTheme.colorScheme.labelAlternative,
                        style = YappTheme.typography.label1NormalRegular,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        if (schedules != null) {
            items(
                items = schedules.dates,
                key = { it.date },
            ) {
                DateGroupedScheduleItem(
                    date = it.date,
                    dayOfWeek = it.dayOfTheWeek,
                    isToday = it.isToday,
                    schedules = it.schedules,
                ) { }
            }
        }
    }
}

@Composable
private fun ScheduleSessionScreen(
    upcomingSessionInfo: UpcomingSessionInfo?,
    sessions: ScheduleList
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = R.string.upcoming_session_section_title),
                        style = YappTheme.typography.headline2Bold,
                        color = YappTheme.colorScheme.labelNormal
                    )

                    if (upcomingSessionInfo != null) {
                        YappChipSmall(
                            text = if (upcomingSessionInfo.remainingDays > 0) {
                                stringResource(
                                    id = R.string.d_day_remaining,
                                    upcomingSessionInfo.remainingDays
                                )
                            } else {
                                stringResource(id = R.string.d_day)
                            },
                            colorType = ChipColorType.Main,
                            isFill = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (upcomingSessionInfo != null) {
                    UpcomingSessionSection(
                        id = upcomingSessionInfo.sessionId,
                        title = upcomingSessionInfo.name,
                        date = upcomingSessionInfo.startDate,
                        dayOfWeek = upcomingSessionInfo.startDayOfTheWeek,
                        location = upcomingSessionInfo.location,
                        startTime = upcomingSessionInfo.startTime,
                        endTime = upcomingSessionInfo.endTime,
                        onClick = {}
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        text = stringResource(id = R.string.upcoming_session_empty_message),
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

        items(
            items = sessions.dates,
            key = { it.date },
        ) {
            DateGroupedScheduleItem(
                date = it.date,
                dayOfWeek = it.dayOfTheWeek,
                isToday = it.isToday,
                showMonth = true,
                schedules = it.schedules,
            ) { }
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
                    singleClick = false,
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
                singleClick = false,
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
        ScheduleScreen(scheduleState = ScheduleState())
    }
}
