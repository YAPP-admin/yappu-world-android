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
    isNotToday: Boolean,
    upcomingSession: UpcomingSessionInfo?
) {
    val (icon, text) = when {
        isNotToday.not() -> coreDesignR.drawable.shake_hands to stringResource(R.string.home_attendance_today)
        isNotToday && upcomingSession == null -> coreDesignR.drawable.session_empty to stringResource(R.string.home_attendance_no_upcoming)
        else -> coreDesignR.drawable.shake_hands to stringResource(R.string.home_attendance_prepare)
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = YappTheme.colorScheme.orange99,
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null
            )
            Text(text, style = YappTheme.typography.label1NormalMedium)
        }
    }
}