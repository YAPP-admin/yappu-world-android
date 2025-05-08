package com.yapp.feature.schedule.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme

@Composable
internal fun AssignmentItem(
    id: String,
    title: String,
    content: String,
    onClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .yappClickable { onClick(id) }
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
            onClick = {}
        )
    }
}