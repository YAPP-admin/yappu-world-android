package com.yapp.feature.schedule.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.extension.dashedBorder

private val ASSIGNMENT_ITEM_CORNER_RADIUS = 12.dp

@Composable
internal fun AssignmentItem(
    id: String,
    title: String,
    content: String,
    dateState: SessionDateState,
    onClick: (String) -> Unit,
) {
    val backgroundColor = when (dateState) {
        SessionDateState.TODAY -> YappTheme.colorScheme.orange99
        SessionDateState.PAST -> YappTheme.colorScheme.backgroundElevatedAlternative
        SessionDateState.FUTURE -> YappTheme.colorScheme.staticWhite
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(ASSIGNMENT_ITEM_CORNER_RADIUS))
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(ASSIGNMENT_ITEM_CORNER_RADIUS),
            )
            .then(
                if (dateState == SessionDateState.FUTURE) {
                    Modifier.dashedBorder(
                        color = YappTheme.colorScheme.labelDisable,
                        strokeWidth = 1.dp,
                        dashLength = 2.dp,
                        gapLength = 2.dp,
                        cornerRadius = ASSIGNMENT_ITEM_CORNER_RADIUS
                    )
                } else {
                    Modifier
                }
            )
            .yappClickable { onClick(id) }
            .padding(
                horizontal = 12.dp,
                vertical = 10.dp,
            )
    ) {
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

@Preview(
    showBackground = true
)
@Composable
private fun AssignmentItemPreview() {
    YappTheme {
        AssignmentItem(
            id = "1",
            title = "과제 제목",
            content = "과제 내용",
            dateState = SessionDateState.TODAY,
            onClick = {}
        )
    }
}