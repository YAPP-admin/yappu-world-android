package com.yapp.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.R
import com.yapp.model.AttendanceStatus
import com.yapp.model.ScheduleProgressPhase

@Composable
fun ScheduleStatusChip(
    scheduleProgressPhase: ScheduleProgressPhase,
    attendanceStatus: AttendanceStatus?,
) {
    val (textResId, backgroundColor, textColor) = when {
        attendanceStatus != null -> when (attendanceStatus) {
            AttendanceStatus.ATTENDED -> Triple(
                R.string.attendance_status_attended,
                YappTheme.colorScheme.accentLightBlueWeak,
                YappTheme.colorScheme.accentLightBlue
            )
            AttendanceStatus.LATE -> Triple(
                R.string.attendance_status_late,
                YappTheme.colorScheme.semanticFillAlternative,
                YappTheme.colorScheme.coolNeutral50
            )
            AttendanceStatus.ABSENT -> Triple(
                R.string.attendance_status_absent,
                YappTheme.colorScheme.accentRedWeak,
                YappTheme.colorScheme.accentRed
            )
            AttendanceStatus.EARLY_LEAVE -> Triple(
                R.string.attendance_status_early_leave,
                YappTheme.colorScheme.accentVioletWeak,
                YappTheme.colorScheme.accentViolet
            )
            AttendanceStatus.EXCUSED -> Triple(
                R.string.attendance_status_excused,
                YappTheme.colorScheme.neutral95,
                YappTheme.colorScheme.neutral40
            )
            else -> null
        }
        scheduleProgressPhase == ScheduleProgressPhase.PENDING -> Triple(
            R.string.attendance_status_scheduled,
            YappTheme.colorScheme.yellow95,
            YappTheme.colorScheme.secondaryNormal
        )
        else -> null
    } ?: return

    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = stringResource(id = textResId),
            color = textColor,
            style = YappTheme.typography.caption2Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScheduleStatusChipPreview() {
    YappTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ScheduleStatusChip(
                scheduleProgressPhase = ScheduleProgressPhase.PENDING,
                attendanceStatus = AttendanceStatus.ATTENDED,
            )
            ScheduleStatusChip(
                scheduleProgressPhase = ScheduleProgressPhase.TODAY,
                attendanceStatus = AttendanceStatus.ATTENDED,
            )
            ScheduleStatusChip(
                scheduleProgressPhase = ScheduleProgressPhase.PENDING,
                attendanceStatus = AttendanceStatus.LATE,
            )
            ScheduleStatusChip(
                scheduleProgressPhase = ScheduleProgressPhase.PENDING,
                attendanceStatus = AttendanceStatus.ABSENT,
            )
            ScheduleStatusChip(
                scheduleProgressPhase = ScheduleProgressPhase.DONE,
                attendanceStatus = AttendanceStatus.EARLY_LEAVE,
            )
            ScheduleStatusChip(
                scheduleProgressPhase = ScheduleProgressPhase.DONE,
                attendanceStatus = AttendanceStatus.EXCUSED,
            )
        }
    }
}