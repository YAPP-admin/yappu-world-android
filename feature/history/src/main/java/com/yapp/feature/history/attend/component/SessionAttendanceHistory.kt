package com.yapp.feature.history.attend.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.AttendanceHistoryItem
import com.yapp.feature.history.R
import com.yapp.model.AttendanceHistoryList

@Composable
internal fun SessionAttendanceHistory(
    attendanceHistoryList: AttendanceHistoryList
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.session_attendance_title),
            style = YappTheme.typography.headline1Bold,
            color = YappTheme.colorScheme.labelNormal
        )

        Spacer(modifier = Modifier.height(8.dp))

        attendanceHistoryList.histories.forEachIndexed { index, attendanceHistory ->
            AttendanceHistoryItem(
                date = attendanceHistory.checkedInAt,
                attendanceStatus = attendanceHistory.attendanceStatus
            )
            if (index != attendanceHistoryList.histories.size - 1) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = YappTheme.colorScheme.lineNormalAlternative
                )
            }
        }
    }
}
