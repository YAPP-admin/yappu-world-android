package com.yapp.feature.schedule.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.feature.schedule.ScheduleTab

@Composable
fun ScheduleTabRow(
    modifier: Modifier = Modifier,
    containerColor: Color = YappTheme.colorScheme.staticWhite,
    selectedTab: ScheduleTab,
    tabList: List<ScheduleTab> = listOf(ScheduleTab.ALL, ScheduleTab.SESSION),
    onTabSelected: (ScheduleTab) -> Unit
) {
    Box(
        modifier = modifier.background(containerColor),
        contentAlignment = Alignment.BottomCenter
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(YappTheme.colorScheme.labelAssistive)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            tabList.forEach { tab ->
                ScheduleTab(
                    labelResId = tab.labelResId,
                    selected = tab.index == selectedTab.index,
                    onClick = { onTabSelected(tab) }
                )
            }
        }
    }
}

@Composable
private fun ScheduleTab(
    modifier: Modifier = Modifier,
    labelResId: Int,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val textColor = if (selected) YappTheme.colorScheme.labelNormal else YappTheme.colorScheme.labelAssistive

    Box(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .background(Color.Transparent)
            .yappClickable { onClick() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = stringResource(id = labelResId),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = YappTheme.typography.body1ReadingRegular,
            color = textColor
        )

        if (selected) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(YappTheme.colorScheme.primaryNormal)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview
@Composable
private fun ScheduleTabRowPreview() {
    val tabList = ScheduleTab.entries
    var selectedTab by remember { mutableStateOf(ScheduleTab.ALL) }

    YappTheme {
        ScheduleTabRow(
            selectedTab = selectedTab,
            tabList = tabList,
            onTabSelected = { selectedTab = it }
        )
    }
}