package com.yapp.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.feature.home.R
import com.yapp.core.designsystem.R as coreDesignR


@Composable
internal fun OtherSection(
    modifier: Modifier = Modifier,
    clickAttendance: () -> Unit,
    clickTotalSession: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .yappClickable(onClick = { clickAttendance() })
                .background(color = YappTheme.colorScheme.staticWhite)
        ) {
            Image(
                modifier = Modifier.align(Alignment.CenterEnd),
                painter = painterResource(coreDesignR.drawable.home_attance_point),
                contentDescription = null
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.home_other_attendance_score_title),
                    style = YappTheme.typography.body1NormalBold
                )
                Text(
                    text = stringResource(R.string.home_other_check_link),
                    style = YappTheme.typography.caption1Regular,
                    color = YappTheme.colorScheme.labelAlternative
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .yappClickable(onClick = { clickTotalSession() })
                .background(color = YappTheme.colorScheme.staticWhite)
        ) {
            Image(
                modifier = Modifier.align(Alignment.CenterEnd),
                painter = painterResource(coreDesignR.drawable.home_all_sessions),
                contentDescription = null
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.home_other_total_session_title),
                    style = YappTheme.typography.body1NormalBold
                )
                Text(
                    text = stringResource(R.string.home_other_check_link),
                    style = YappTheme.typography.caption1Regular,
                    color = YappTheme.colorScheme.labelAlternative
                )
            }
        }
    }
}

@Preview
@Composable
private fun OtherSectionPreview() {
    YappTheme {
        OtherSection(
            clickAttendance = {},
            clickTotalSession = {}
        )
    }
}
