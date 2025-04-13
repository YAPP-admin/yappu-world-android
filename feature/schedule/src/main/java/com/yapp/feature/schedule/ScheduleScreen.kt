package com.yapp.feature.schedule

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Composable
internal fun ScheduleRoute(

) {
    ScheduleScreen()
}

@Composable
internal fun ScheduleScreen(

) {
    Column {
        ScheduleHeader()
    }
}

@Composable
private fun ScheduleHeader(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 12.dp
            )
    ) {
        Text(
            text = stringResource(id = R.string.schedule_screen_title),
            style = YappTheme.typography.heading1Bold,
            color = YappTheme.colorScheme.labelNormal,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScheduleHeaderPreview() {
    YappTheme {
        ScheduleRoute()
    }
}