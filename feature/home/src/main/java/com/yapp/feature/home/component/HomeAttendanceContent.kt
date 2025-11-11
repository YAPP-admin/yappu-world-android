package com.yapp.feature.home.component

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.solid.SolidButtonDefaults
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonLarge
import com.yapp.core.designsystem.component.chip.ChipColorType
import com.yapp.core.designsystem.component.chip.YappChipSmall
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.util.formatTimeRange
import com.yapp.feature.home.R
import com.yapp.model.AttendanceStatus
import com.yapp.model.NoticeInfo
import com.yapp.model.NoticeType
import com.yapp.model.UpcomingSessionInfo
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.yapp.core.designsystem.R as coreDesignR

@Composable
internal fun HomeAttendanceContent(
    modifier: Modifier = Modifier,
    upcomingSession: UpcomingSessionInfo?,
    notices: List<NoticeInfo>,
    onClickAttend: () -> Unit,
    onClickNotice: (String) -> Unit,
) {
    val isToday = upcomingSession?.remainingDays == 0

    Box(modifier = modifier.fillMaxWidth()) {
        if (isToday) {
            TodaySessionCard(
                session = upcomingSession,
                notices = notices,
                onClickAttend = onClickAttend,
                onClickNotice = onClickNotice,
            )
        } else if (upcomingSession != null) {
            UpcomingSessionCard(session = upcomingSession)
        } else {
            EmptyNextSessionCard()
        }
    }
}

@Composable
private fun EmptyNextSessionCard() {
    val today = remember { LocalDate.now() }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.session_today_text, today.format(DATE_OUTPUT)),
            style = YappTheme.typography.headline1Bold,
            color = YappTheme.colorScheme.labelNormal
        )

        /***
         *  내려오는 다음 세션이 없기에 강제화 처리를 위해 true, null 값 지정
         ***/
        HomeAttendanceNotice(
            isNotToday = true,
            upcomingSession = null
        )
    }
}

@Composable
private fun UpcomingSessionCard(
    session: UpcomingSessionInfo
) {
    val today = remember { LocalDate.now() }
    val parsedDate = remember(session.startDate) {
        formatSessionDate(session.startDate)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("upcomingSessionCard"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.session_today_text, today.format(DATE_OUTPUT)),
            style = YappTheme.typography.headline1Bold,
            color = YappTheme.colorScheme.labelNormal
        )

        HomeAttendanceNotice(
            isNotToday = parsedDate?.let { today.isEqual(it) } ?: true,
            upcomingSession = session
        )

        YappSolidPrimaryButtonLarge(
            modifier = Modifier.fillMaxWidth(),
            text = parsedDate?.let {
                stringResource(R.string.session_scheduled).let { scheduled ->
                    "${it.format(DATE_OUTPUT)} $scheduled"
                }
            } ?: stringResource(R.string.session_date_error),
            enable = false,
            onClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodaySessionCard(
    session: UpcomingSessionInfo,
    notices: List<NoticeInfo>,
    onClickAttend: () -> Unit,
    onClickNotice: (id: String) -> Unit,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isAttended = session.status != null

    val duration = remember(
        configuration,
        session
    ) {
        formatTimeRange(
            context = context,
            startTime = session.startTime,
            endTime = session.endTime,
        )
    }

    val startTime = remember(session) {
        formatStartTime(context, startTime = session.startTime)
    }

    Column {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = session.name,
                style = YappTheme.typography.headline1Bold,
                color = YappTheme.colorScheme.labelNormal
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    painter = painterResource(id = coreDesignR.drawable.icon_location),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = session.location.orEmpty(),
                    style = YappTheme.typography.caption1Regular,
                    color = YappTheme.colorScheme.labelAlternative
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    painter = painterResource(id = coreDesignR.drawable.icon_time),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = duration.orEmpty(),
                    style = YappTheme.typography.caption1Regular,
                    color = YappTheme.colorScheme.labelAlternative
                )
            }

            if (session.status == null) {
                HomeAttendanceNotice(
                    isNotToday = false,
                    upcomingSession = session
                )
            }

            if (isAttended) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    YappChipSmall(
                        text = stringResource(id = R.string.session_in_progress),
                        colorType = ChipColorType.Gray,
                        isFill = true
                    )
                    Text(
                        text = startTime,
                        style = YappTheme.typography.label1NormalMedium,
                        color = YappTheme.colorScheme.labelAssistive
                    )
                }
            }

            session.status?.let { status ->
                val message = when (status) {
                    AttendanceStatus.ATTENDED -> stringResource(R.string.session_attendance_done)
                    AttendanceStatus.LATE -> stringResource(R.string.session_attendance_late)
                    AttendanceStatus.ABSENT -> stringResource(R.string.session_attendance_absent)
                    AttendanceStatus.EARLY_LEAVE -> stringResource(R.string.session_attendance_early_leave)
                    AttendanceStatus.EXCUSED -> stringResource(R.string.session_attendance_excused)
                }

                YappSolidPrimaryButtonLarge(
                    modifier = Modifier.fillMaxWidth(),
                    text = message,
                    enable = false,
                    colors = SolidButtonDefaults.colorsPrimary.copy(
                        disableBackgroundColor = YappTheme.colorScheme.orange99,
                        disableTextColor = YappTheme.colorScheme.primaryNormal
                    ),
                    onClick = {}
                )
            } ?: run {
                val buttonText = if (session.canCheckIn) {
                    stringResource(R.string.session_attendance)
                } else {
                    stringResource(R.string.session_attendance_not_yet_message)
                }

                YappSolidPrimaryButtonLarge(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("attendanceButton"),
                    text = buttonText,
                    enable = session.canCheckIn,
                    onClick = { if (session.canCheckIn) onClickAttend() },
                )
            }
        }

        if (notices.isNotEmpty()) {
            Spacer(Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(Modifier.height(24.dp))

            Text(text = "세션 공지", style = YappTheme.typography.label2Bold, color = YappTheme.colorScheme.labelAlternative)

            Spacer(Modifier.height(8.dp))

            notices.forEachIndexed { index, item ->
                key(item.id) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .yappClickable(onClick = { onClickNotice(item.id) })
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = item.title)
                        Icon(
                            painterResource(coreDesignR.drawable.icon_chevron_right),
                            contentDescription = null
                        )
                    }
                    if (index < notices.lastIndex) {
                        HorizontalDivider(color = YappTheme.colorScheme.lineNormalAlternative)
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeAttendanceContentPreview() {
    YappTheme {
        Column {
            HomeAttendanceContent(
                upcomingSession = UpcomingSessionInfo(
                    sessionId = "019a1190-4db2-34d7-bbbe-2b0bf56810fb",
                    name = "23기 테스트",
                    startDate = "2025-10-23",
                    startDayOfTheWeek = "목",
                    endDate = "2025-10-23",
                    endDayOfTheWeek = "목",
                    startTime = "17:10:00",
                    endTime = "18:40:00",
                    location = "KT&G상상플래닛",
                    remainingDays = 0,
                    canCheckIn = false,
                    status = null,
                ),
                notices = listOf(
                    NoticeInfo(
                        id="12345",
                        writerName="김동현",
                        writerId="donghyun123",
                        writerPosition="팀장",
                        writerGeneration=5,
                        createdAt="2025-11-11T14:30:00",
                        title="2025년 하반기 프로젝트 계획 안내",
                        content="2025년 하반기 프로젝트 일정 및 계획에 대한 공지입니다. 각 팀은 본 공지를 확인하고 준비해 주세요.",
                        noticeType= NoticeType.ALL
                    ),
                    NoticeInfo(
                        id="12346",
                        writerName="김동현",
                        writerId="donghyun123",
                        writerPosition="팀장",
                        writerGeneration=5,
                        createdAt="2025-11-11T14:30:00",
                        title="2025년 하반기 프로젝트 계획 안내",
                        content="2025년 하반기 프로젝트 일정 및 계획에 대한 공지입니다. 각 팀은 본 공지를 확인하고 준비해 주세요.",
                        noticeType= NoticeType.ALL
                    )
                ),
                onClickAttend = {},
                onClickNotice = {}
            )

            /*
            UpcomingSessionCard(
                session = UpcomingSessionInfo(
                    sessionId = "019a1190-4db2-34d7-bbbe-2b0bf56810fb",
                    name = "23기 테스트",
                    startDate = "2025-10-23",
                    startDayOfTheWeek = "목",
                    endDate = "2025-10-23",
                    endDayOfTheWeek = "목",
                    startTime = "17:10:00",
                    endTime = "18:40:00",
                    location = "KT&G상상플래닛",
                    remainingDays = 2,
                    canCheckIn = false,
                    status = null,
                )
            )
            Spacer(Modifier.height(40.dp))
            EmptyNextSessionCard()*/
        }
    }
}

private val HH_MM_SS_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss")
private val HH_MM_FORMATTER = DateTimeFormatter.ofPattern("HH:mm")

val DATE_INPUT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
val DATE_OUTPUT: DateTimeFormatter = DateTimeFormatter.ofPattern("M월 d일")

private fun formatStartTime(context: Context, startTime: String?): String {
    if (startTime.isNullOrBlank()) {
        return context.getString(R.string.session_time_missing)
    }
    return runCatching {
        val time = LocalTime.parse(startTime, HH_MM_SS_FORMATTER)
        "${time.format(HH_MM_FORMATTER)}~"
    }.getOrElse {
        context.getString(R.string.session_time_missing)
    }
}

private fun formatSessionDate(date: String): LocalDate? =
    runCatching { LocalDate.parse(date, DATE_INPUT) }.getOrNull()