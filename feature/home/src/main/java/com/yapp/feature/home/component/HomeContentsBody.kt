package com.yapp.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.feature.home.R
import com.yapp.model.UpcomingSessionInfo
import com.yapp.core.designsystem.R as coreDesignR

@Composable
internal fun HomeAttendanceNotice(
    modifier: Modifier = Modifier,
    upcomingSession: UpcomingSessionInfo?
) {
    val colorSteps = arrayOf(
        0.2f to YappTheme.colorScheme.primaryNormal,
        1f to YappTheme.colorScheme.secondaryNormal
    )

    val text = stringResource(
        id = when {
            upcomingSession == null -> R.string.home_attendance_no_upcoming
            upcomingSession.remainingDays == 0 -> R.string.home_attendance_today
            else -> R.string.home_attendance_prepare
        }
    )

    Box(
        modifier = Modifier.background(brush = Brush.horizontalGradient(colorStops = colorSteps))
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(YappTheme.colorScheme.staticWhite)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .background(
                        color = YappTheme.colorScheme.orange99,
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(coreDesignR.drawable.home_attandance),
                    contentDescription = null
                )
                Text(text, style = YappTheme.typography.label1NormalMedium)
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}