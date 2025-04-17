package com.yapp.feature.schedule.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun ScheduleTabRow(
    modifier: Modifier = Modifier,
    containerColor: Color = YappTheme.colorScheme.staticWhite,
    selectedTabIndex: Int,
    tabList: List<String>,
    onTabSelected: (Int) -> Unit
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
            tabList.forEachIndexed { index, tab ->
                ScheduleTab(
                    label = tab,
                    selected = selectedTabIndex == index,
                    onClick = {
                        onTabSelected(index)
                    }
                )
            }
        }
    }
}

@Composable
private fun ScheduleTab(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val textColor = if (selected) YappTheme.colorScheme.labelNormal else YappTheme.colorScheme.labelAssistive

    Box(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .background(Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = label,
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
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    YappTheme {
        ScheduleTabRow(
            selectedTabIndex = selectedTabIndex,
            tabList = listOf("전체", "세션"),
            onTabSelected = { selectedTabIndex = it }
        )
    }
}