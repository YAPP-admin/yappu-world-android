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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

enum class AttendanceStatus {
    SCHEDULED,
    ATTENDED,
    LATE
}

@Composable
fun AttendanceStatusChip(
    status: AttendanceStatus,
) {
    val text = when (status) {
        AttendanceStatus.SCHEDULED -> "예정"
        AttendanceStatus.ATTENDED -> "출석"
        AttendanceStatus.LATE -> "지각"
    }

    val backgroundColor = when (status) {
        AttendanceStatus.SCHEDULED -> YappTheme.colorScheme.yellow95
        AttendanceStatus.ATTENDED -> YappTheme.colorScheme.accentLightBlueWeak
        AttendanceStatus.LATE -> YappTheme.colorScheme.coolNeutral50.copy(alpha = 0.05f)
    }

    val textColor = when (status) {
        AttendanceStatus.SCHEDULED -> YappTheme.colorScheme.secondaryNormal
        AttendanceStatus.ATTENDED -> YappTheme.colorScheme.accentLightBlue
        AttendanceStatus.LATE -> YappTheme.colorScheme.coolNeutral50
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
        }
    }
}