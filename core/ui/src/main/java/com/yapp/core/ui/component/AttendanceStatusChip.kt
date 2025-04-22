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

@Composable
fun AttendanceStatusChip(
    status: AttendanceStatus,
) {
    val text = when (status) {
        AttendanceStatus.SCHEDULED -> stringResource(id = R.string.attendance_status_scheduled)
        AttendanceStatus.ATTENDED -> stringResource(id = R.string.attendance_status_attended)
        AttendanceStatus.LATE -> stringResource(id = R.string.attendance_status_late)
        AttendanceStatus.ABSENT -> stringResource(id = R.string.attendance_status_absent)
        AttendanceStatus.EARLY_LEAVE -> stringResource(id = R.string.attendance_status_early_leave)
        AttendanceStatus.EXCUSED -> stringResource(id = R.string.attendance_status_excused)
    }

    val backgroundColor = when (status) {
        AttendanceStatus.SCHEDULED -> YappTheme.colorScheme.yellow95
        AttendanceStatus.ATTENDED -> YappTheme.colorScheme.accentLightBlueWeak
        AttendanceStatus.LATE -> YappTheme.colorScheme.semanticFillAlternative
        AttendanceStatus.ABSENT -> YappTheme.colorScheme.accentRedWeak
        AttendanceStatus.EARLY_LEAVE -> YappTheme.colorScheme.accentVioletWeak
        AttendanceStatus.EXCUSED -> YappTheme.colorScheme.neutral95
    }

    val textColor = when (status) {
        AttendanceStatus.SCHEDULED -> YappTheme.colorScheme.secondaryNormal
        AttendanceStatus.ATTENDED -> YappTheme.colorScheme.accentLightBlue
        AttendanceStatus.LATE -> YappTheme.colorScheme.coolNeutral50
        AttendanceStatus.ABSENT -> YappTheme.colorScheme.accentRed
        AttendanceStatus.EARLY_LEAVE -> YappTheme.colorScheme.accentViolet
        AttendanceStatus.EXCUSED -> YappTheme.colorScheme.neutral40
    }

    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(
                horizontal = 8.dp,
                vertical = 2.dp
            )
    ) {
        Text(
            text = text,
            color = textColor,
            style = YappTheme.typography.caption2Medium
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun AttendanceStatusChipPreview() {
    YappTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            AttendanceStatusChip(
                status = AttendanceStatus.SCHEDULED
            )

            AttendanceStatusChip(
                status = AttendanceStatus.ATTENDED
            )

            AttendanceStatusChip(
                status = AttendanceStatus.LATE
            )

            AttendanceStatusChip(
                status = AttendanceStatus.ABSENT
            )

            AttendanceStatusChip(
                status = AttendanceStatus.EARLY_LEAVE
            )

            AttendanceStatusChip(
                status = AttendanceStatus.EXCUSED
            )
        }
    }
}