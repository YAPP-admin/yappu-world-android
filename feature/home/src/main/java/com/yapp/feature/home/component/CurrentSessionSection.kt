package com.yapp.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.SessionChip
import com.yapp.feature.home.R
import com.yapp.model.SessionProgressPhase
import com.yapp.model.UpcomingSessionInfo

@Composable
internal fun CurrentSessionSection(
    modifier: Modifier = Modifier,
    upcomingSession: UpcomingSessionInfo?,
    onClickDetail: (String) -> Unit,
    onClickAttend: () -> Unit,
    onClickNotice: (String) -> Unit,
) {
    Column {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.session_today_title),
                    style = YappTheme.typography.label1NormalBold,
                    color = YappTheme.colorScheme.labelAlternative
                )

                SessionChip(progressPhase = upcomingSession?.progressPhase ?: SessionProgressPhase.NONE)
            }
            if (upcomingSession?.sessionId != null) {
                Text(
                    modifier = Modifier
                        .testTag("upcomingSessionDetailButton")
                        .yappClickable(onClick = { onClickDetail(upcomingSession.sessionId) }),
                    text = stringResource(R.string.session_today_detail),
                    style = YappTheme.typography.label1NormalBold,
                    color = YappTheme.colorScheme.primaryNormal
                )
            }
        }

        Spacer(Modifier.height(21.dp))

        HomeAttendanceContent(
            upcomingSession = upcomingSession,
            onClickAttend = onClickAttend,
            onClickNotice = onClickNotice,
        )
    }
}
