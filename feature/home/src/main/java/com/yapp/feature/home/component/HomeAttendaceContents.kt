package com.yapp.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.yapp.core.designsystem.component.button.solid.SolidButtonDefaults
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonLarge
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.extension.dashedBorder
import com.yapp.model.AttendanceStatus
import com.yapp.model.UpcomingSessionInfo
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.yapp.core.designsystem.R as coreDesignR

@Composable
internal fun HomeAttendanceContents(
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
                text = "모든 세션이 종료되었어요.\n기수 활동에 참여해 주셔서 감사합니다 :)",
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
    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormat = DateTimeFormatter.ofPattern("M월 d일")

    val parsedDate = remember(upcomingDate) {
        runCatching { LocalDate.parse(upcomingDate, inputFormat) }.getOrNull()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "오늘은 ${today.format(outputFormat)}이에요.",
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
                text = parsedDate?.format(outputFormat)?.plus(" 세션 예정") ?: "날짜 정보를 불러올 수 없어요.",
                style = YappTheme.typography.body2NormalBold,
                color = YappTheme.colorScheme.labelAssistive
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodaySessionCard(
    session: UpcomingSessionInfo,
    onClickAttend: () -> Unit
) {
    val parsedTime = runCatching {
        LocalTime.parse(session.startTime, DateTimeFormatter.ofPattern("HH:mm:ss"))
    }.getOrNull()

    val displayTime = parsedTime?.format(DateTimeFormatter.ofPattern("a h시 mm분", Locale.KOREAN))
        ?.replace("AM", "오전")
        ?.replace("PM", "오후") ?: "시간 미정"

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
                    text = "$displayTime 오픈",
                    style = YappTheme.typography.label1NormalMedium,
                    color = YappTheme.colorScheme.primaryNormal
                )
            }


        }

        if (session.status == AttendanceStatus.ATTENDED) {
            YappSolidPrimaryButtonLarge(
                modifier = Modifier.fillMaxWidth(),
                text = "출석완료!",
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
                    text = "출석하기",
                    enable = true,
                    onClick = onClickAttend
                )
            }
        }
    }
}