package com.yapp.feature.home.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.solid.SolidButtonDefaults
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonLarge
import com.yapp.core.designsystem.component.chip.ChipColorType
import com.yapp.core.designsystem.component.chip.YappChipSmall
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.extension.dashedBorder
import com.yapp.core.ui.util.formatToKoreanTime
import com.yapp.feature.home.R
import com.yapp.model.UpcomingSessionInfo
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.yapp.core.designsystem.R as coreDesignR

@Composable
internal fun HomeAttendanceContent(
    modifier: Modifier = Modifier,
    upcomingSession: UpcomingSessionInfo?,
    onClickAttend: () -> Unit
) {
    val isToday = upcomingSession?.remainingDays == 0

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .dashedBorder(
                color = Color(0xFFFED9CB),
                dashLength = 3.dp,
                cornerRadius = 10.dp
            )
            .padding(20.dp)
    ) {
        if (upcomingSession == null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.session_ended_message),
                textAlign = TextAlign.Center,
                style = YappTheme.typography.caption1Medium,
                color = YappTheme.colorScheme.labelNormal,
            )
        } else {
            if (isToday) {
                TodaySessionCard(
                    session = upcomingSession,
                    onClickAttend = onClickAttend
                )
            } else {
                UpcomingSessionCard(
                    today = LocalDate.now(),
                    upcomingDate = upcomingSession.startDate
                )
            }
        }
    }
}

@Composable
private fun UpcomingSessionCard(
    today: LocalDate,
    upcomingDate: String
) {
    val parsedDate = remember(upcomingDate) {
        formatSessionDate(upcomingDate)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.session_today_text, today.format(DATE_OUTPUT)),
            style = YappTheme.typography.label2Medium,
            color = YappTheme.colorScheme.labelNormal
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
    onClickAttend: () -> Unit
) {
    val context = LocalContext.current

    val isAttended = session.status != null

    val timeStr = if (isAttended) session.endTime else session.startTime
    val displayTime = timeStr?.let { formatToKoreanTime(context, it) }
        ?: stringResource(R.string.session_date_error)

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = session.name,
            style = YappTheme.typography.label2Medium,
            color = YappTheme.colorScheme.labelNormal
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    painter = painterResource(id = coreDesignR.drawable.icon_time),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = YappTheme.colorScheme.primaryNormal)
                )

                Text(
                    text = if (isAttended)
                        stringResource(R.string.session_time_end, displayTime)
                    else
                        stringResource(R.string.session_time_open, displayTime),
                    style = YappTheme.typography.label1NormalMedium,
                    color = YappTheme.colorScheme.primaryNormal
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
                        text = formatStartTime(context, session.startTime),
                        style = YappTheme.typography.label1NormalMedium,
                        color = YappTheme.colorScheme.labelAssistive
                    )
                }
            }
        }

        if (isAttended) {
            YappSolidPrimaryButtonLarge(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.session_attendance_done),
                enable = false,
                colors = SolidButtonDefaults.colorsPrimary.copy(
                    disableBackgroundColor = YappTheme.colorScheme.orange99,
                    disableTextColor = YappTheme.colorScheme.primaryNormal
                ),
                onClick = {}
            )
        } else {
            if (session.canCheckIn) {
                YappSolidPrimaryButtonLarge(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.session_attendance),
                    enable = true,
                    onClick = onClickAttend
                )
            } else {
                YappSolidPrimaryButtonLarge(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.session_attendance_not_yet_message),
                    enable = false,
                    onClick = {}
                )
            }
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