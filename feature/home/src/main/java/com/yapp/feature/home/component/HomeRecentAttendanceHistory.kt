package com.yapp.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.ScheduleStatusChip
import com.yapp.model.AttendanceHistoryList
import com.yapp.model.AttendanceStatus
import com.yapp.model.ScheduleProgressPhase

@Composable
internal fun HomeRecentAttendanceHistory(
    modifier: Modifier = Modifier,
    recentAttendanceHistory: AttendanceHistoryList
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = YappTheme.colorScheme.staticWhite)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "전체 출석 내역",
                style = YappTheme.typography.headline1Bold,
                color = YappTheme.colorScheme.labelNormal
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        recentAttendanceHistory.histories.forEachIndexed { index, attendanceHistory ->
            HomeRecentAttendanceHistoryItem(
                date = attendanceHistory.checkedInAt ?: "2023.10.01",
                attendanceStatus = attendanceHistory.attendanceStatus
            )
            if (index != recentAttendanceHistory.histories.size - 1) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = YappTheme.colorScheme.lineNormalAlternative
                )
            }
        }
    }
}

@Composable
private fun HomeRecentAttendanceHistoryItem(
    date: String,
    attendanceStatus: AttendanceStatus
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = date,
            style = YappTheme.typography.label2Regular,
            color = YappTheme.colorScheme.labelAlternative
        )

        ScheduleStatusChip(
            scheduleProgressPhase = ScheduleProgressPhase.DONE,
            attendanceStatus = attendanceStatus
        )
    }
}

@Preview
@Composable
private fun HomeRecentAttendanceHistoryItemPreview() {
    HomeRecentAttendanceHistoryItem(
        date = "2023.10.01",
        attendanceStatus = AttendanceStatus.ATTENDED
    )
}