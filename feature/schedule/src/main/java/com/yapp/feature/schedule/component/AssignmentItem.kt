package com.yapp.feature.schedule.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme

@Composable
internal fun AssignmentItem(
    id: Long,
    title: String,
    content: String,
    date: String,
    dayOfWeek: String,
    isToday: Boolean = false,
    isPast: Boolean = false,
    onClick: (Long) -> Unit,
) {
    val dateColor = if (isToday) {
        YappTheme.colorScheme.primaryNormal
    } else {
        YappTheme.colorScheme.labelNeutral
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .yappClickable { onClick(id) }
            .graphicsLayer { if (isPast) alpha = 0.5f }
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = date,
                style = YappTheme.typography.body1NormalBold,
                color = dateColor
            )

            Text(
                text = "(${dayOfWeek})",
                style = YappTheme.typography.caption1Medium,
                color = dateColor
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Column(modifier = Modifier.weight(1f)) {
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = title,
                style = YappTheme.typography.label1NormalBold,
                color = YappTheme.colorScheme.labelNormal
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = content,
                style = YappTheme.typography.caption1Regular,
                color = YappTheme.colorScheme.labelAlternative
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun AssignmentItemPreview() {
    YappTheme {
        AssignmentItem(
            id = 0,
            title = "과제 제목",
            content = "과제 내용",
            date = "1",
            dayOfWeek = "금",
            isToday = false,
            isPast = true,
            onClick = {}
        )
    }
}