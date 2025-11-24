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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
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
import com.yapp.core.ui.component.LocalBottomBarHeight
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.schedule.component.DateGroupedScheduleItem
import com.yapp.feature.schedule.component.ScheduleGroupVariant
import com.yapp.feature.schedule.component.ScheduleTabRow
import com.yapp.feature.schedule.component.UpcomingSessionSection
import com.yapp.model.AttendanceStatus
import com.yapp.model.ScheduleInfo
import com.yapp.model.ScheduleList
import com.yapp.model.ScheduleProgressPhase
import com.yapp.model.ScheduleType
import com.yapp.model.SessionType

@Composable
internal fun ScheduleRoute(
    viewModel: ScheduleViewModel = hiltViewModel(),
    handleException: (Throwable) -> Unit,
    navigateToLogin: () -> Unit,
    navigateToSessionDetail: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.store.onIntent(ScheduleIntent.EnterScheduleScreen)
    }

    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    viewModel.store.sideEffects.collectWithLifecycle { effect ->
        when (effect) {
            is ScheduleSideEffect.HandleException -> handleException(effect.exception)
            ScheduleSideEffect.NavigateToLogin -> navigateToLogin()
            is ScheduleSideEffect.NavigateToSessionDetail -> navigateToSessionDetail(effect.id)
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
    val saveableStateHolder = rememberSaveableStateHolder()

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

                saveableStateHolder.SaveableStateProvider(key = scheduleState.selectedTab) {
                    when (scheduleState.selectedTab) {
                        ScheduleTab.ALL -> {
                            ScheduleAllScreen(
                                selectedYear = scheduleState.selectedYear,
                                selectedMonth = scheduleState.selectedMonth,
                                schedules = scheduleState.schedules[
                                    Pair(scheduleState.selectedYear, scheduleState.selectedMonth)
                                ] ?: ScheduleList(emptyList()),
                                onIntent = onIntent
                            )
                        }

                        ScheduleTab.SESSION -> ScheduleSessionScreen(
                            upcomingSessions = scheduleState.upcomingSessions,
                            sessions = scheduleState.sessions,
                            onIntent = onIntent
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ScheduleAllScreen(
    selectedYear: Int,
    selectedMonth: Int,
    schedules: ScheduleList,
    onIntent: (ScheduleIntent) -> Unit,
) {
    val density = LocalDensity.current
    val bottomBarHeightDp = LocalBottomBarHeight.current

    LazyColumn(
        modifier = Modifier.fillMaxSize()
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
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (schedules.isEmpty) {
            item {
                Column(
                    modifier = Modifier
                        .fillParentMaxSize()
                        .padding(bottom = bottomBarHeightDp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = com.yapp.core.ui.R.drawable.illust_yappu_construction),
                        contentDescription = null,
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.schedule_empty_text),
                        color = YappTheme.colorScheme.labelAlternative,
                        style = YappTheme.typography.label1NormalRegular,
                        textAlign = TextAlign.Center
                    )
                }

            }
        } else {
            itemsIndexed(schedules.dates, key = { index, it -> "${it.date}_$index" }) { index, grouped ->
                DateGroupedScheduleItem(
                    variant = ScheduleGroupVariant.LEFT_ALIGNED,
                    date = grouped.date,
                    dayOfWeek = grouped.dayOfTheWeek,
                    isToday = grouped.isToday,
                    showMonth = true,
                    schedules = grouped.schedules,
                ) { id ->
                    onIntent(ScheduleIntent.ClickSessionItem(id))
                }

                if (index < schedules.dates.lastIndex) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
private fun ScheduleSessionScreen(
    upcomingSessions: List<ScheduleInfo>,
    sessions: ScheduleList,
    onIntent: (ScheduleIntent) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Column(
                modifier = Modifier.padding(vertical = 20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(start = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = R.string.upcoming_session_section_title),
                        style = YappTheme.typography.headline2Bold,
                        color = YappTheme.colorScheme.labelNormal
                    )

                    YappChipSmall(
                        text = stringResource(id = R.string.d_day),
                        colorType = ChipColorType.Main,
                        isFill = true,
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (upcomingSessions.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        text = stringResource(id = R.string.upcoming_session_empty_message),
                        style = YappTheme.typography.label2Regular,
                        color = YappTheme.colorScheme.labelAlternative,
                        textAlign = TextAlign.Center
                    )
                } else {
                    UpcomingSessionSection(
                        sessions = upcomingSessions,
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

        item {
            Text(
                modifier = Modifier.padding(
                    start = 20.dp,
                    top = 20.dp,
                    bottom = 12.dp
                ),
                text = stringResource(id = R.string.session_section_title),
                style = YappTheme.typography.headline2Bold,
                color = YappTheme.colorScheme.labelNormal,
            )
        }

        itemsIndexed(sessions.dates, key = { index, it -> "${it.date}_$index" }) { index, grouped ->
            DateGroupedScheduleItem(
                variant = ScheduleGroupVariant.TOP_ALIGNED,
                date = grouped.date,
                dayOfWeek = grouped.dayOfTheWeek,
                isToday = grouped.isToday,
                showMonth = true,
                schedules = grouped.schedules,
            ) { id ->
                onIntent(ScheduleIntent.ClickSessionItem(id))
            }

            if (index < sessions.dates.lastIndex) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }
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
        ScheduleScreen(
            scheduleState = ScheduleState(
                selectedTab = ScheduleTab.SESSION,
                upcomingSessions = listOf(
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
        )
    }
}
