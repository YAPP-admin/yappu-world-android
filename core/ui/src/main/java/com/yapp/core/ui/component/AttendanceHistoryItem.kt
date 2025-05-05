package com.yapp.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.R
import com.yapp.core.ui.util.formatDateTime
import com.yapp.model.AttendanceStatus
import com.yapp.model.ScheduleProgressPhase

@Composable
fun AttendanceHistoryItem(
    date: String?,
    attendanceStatus: AttendanceStatus
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = date?.let { formatDateTime(context, it) } ?: stringResource(id = R.string.attendance_status_none),
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
fun AttendanceHistoryItemPreview() {
    YappTheme {
        AttendanceHistoryItem(
            date = "2023-10-01T12:00:00",
            attendanceStatus = AttendanceStatus.ATTENDED
        )
    }
}