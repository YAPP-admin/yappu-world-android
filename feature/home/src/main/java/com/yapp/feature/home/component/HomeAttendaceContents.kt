package com.yapp.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonLarge
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.extension.dashedBorder
import com.yapp.feature.home.HomeState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.yapp.core.designsystem.R as coreDesignR

@Composable
internal fun HomeAttendanceContents(
    modifier: Modifier = Modifier,
    attendState: HomeState.AttendState,
    todayOrUpcomingSession: HomeState.Session?,
    buttonTitle: String,
    onClickAttend: () -> Unit
) {
    val today = remember { LocalDate.now() }
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

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
        if (todayOrUpcomingSession == null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "모든 세션이 종료되었어요.\n기수 활동에 참여해 주셔서 감사합니다 :)",
                textAlign = TextAlign.Center,
                style = YappTheme.typography.caption1Medium,
                color = YappTheme.colorScheme.labelNormal,
            )
        } else {
            val sessionDate = runCatching {
                LocalDate.parse(todayOrUpcomingSession.date, formatter)
            }.getOrElse { return }

            if (sessionDate == today) {
                TodaySessionCard(
                    session = todayOrUpcomingSession,
                    attendState = attendState,
                    buttonTitle = buttonTitle,
                    onClickAttend = onClickAttend
                )
            } else {
                UpcomingSessionCard(
                    today = today,
                    upcomingDate = sessionDate
                )
            }
        }
    }
}

@Composable
private fun UpcomingSessionCard(
    today: LocalDate,
    upcomingDate: LocalDate
) {
    val dateFormatter = DateTimeFormatter.ofPattern("M월 d일")

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "오늘은 ${today.format(dateFormatter)}이에요.",
            style = YappTheme.typography.label2Medium,
            color = YappTheme.colorScheme.labelNormal
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = YappTheme.colorScheme.interactionDisable,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${upcomingDate.format(dateFormatter)} 세션예정",
                style = YappTheme.typography.body2NormalBold,
                color = YappTheme.colorScheme.labelAssistive
            )
        }
    }
}

@Composable
fun TodaySessionCard(
    buttonTitle: String,
    session: HomeState.Session,
    attendState: HomeState.AttendState,
    onClickAttend: () -> Unit
) {
    val parsedTime = runCatching {
        LocalTime.parse(session.startTime, DateTimeFormatter.ofPattern("HH:mm"))
    }.getOrNull()

    val displayTime = parsedTime?.format(DateTimeFormatter.ofPattern("a h시 mm분", Locale.KOREAN))
        ?.replace("AM", "오전")
        ?.replace("PM", "오후") ?: "시간 미정"

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = session.title,
            style = YappTheme.typography.caption1Medium,
            color = YappTheme.colorScheme.interactionInactive
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = coreDesignR.drawable.icon_time),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = YappTheme.colorScheme.primaryNormal)
            )

            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$displayTime 오픈",
                style = YappTheme.typography.caption1Medium,
                color = YappTheme.colorScheme.primaryNormal
            )
        }

        when (attendState) {
            HomeState.AttendState.NOT_YET -> {
                YappSolidPrimaryButtonLarge(
                    text = buttonTitle,
                    enable = false,
                    onClick = {}
                )
            }

            HomeState.AttendState.POSSIBLE -> {
                YappSolidPrimaryButtonLarge(
                    text = buttonTitle,
                    enable = true,
                    onClick = { onClickAttend() }
                )
            }

            HomeState.AttendState.COMPLETED -> {
                YappSolidPrimaryButtonLarge(
                    text = buttonTitle,
                    enable = false,
                    onClick = {}
                )
            }
        }
    }
}


