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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.ScheduleStatusChip
import com.yapp.feature.home.R
import com.yapp.model.AttendanceHistory
import com.yapp.model.AttendanceHistoryList
import com.yapp.model.AttendanceStatus
import com.yapp.model.ScheduleProgressPhase

@Composable
internal fun HomeRecentAttendanceHistory(
    modifier: Modifier = Modifier,
    recentAttendanceHistory: AttendanceHistoryList,
    onClickShowAll: () -> Unit = {  }
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
                text = stringResource(id = R.string.home_recent_attendance_title),
                style = YappTheme.typography.headline1Bold,
                color = YappTheme.colorScheme.labelNormal
            )

            Text(
                modifier = Modifier.yappClickable(onClick = onClickShowAll),
                text = stringResource(id = R.string.home_recent_attendance_all_title),
                style = YappTheme.typography.label1NormalRegular,
                color = YappTheme.colorScheme.labelAlternative,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        recentAttendanceHistory.histories.forEachIndexed { index, attendanceHistory ->
            HomeRecentAttendanceHistoryItem(
                name = attendanceHistory.name,
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
    name: String,
    attendanceStatus: AttendanceStatus
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = YappTheme.typography.label2Regular,
            color = YappTheme.colorScheme.labelNeutral
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
    val previewData = AttendanceHistory(
        checkedInAt = "2023.10.01",
        name = "YAPP 스터디",
        attendanceStatus = AttendanceStatus.ATTENDED
    )

    YappTheme {
        HomeRecentAttendanceHistory(
            recentAttendanceHistory = AttendanceHistoryList(
                histories = List(5) { previewData }
            )
        )
    }
}