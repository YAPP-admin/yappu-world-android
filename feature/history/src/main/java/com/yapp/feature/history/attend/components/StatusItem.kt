package com.yapp.feature.history.attend.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Composable
internal fun StatusItem(
    modifier: Modifier = Modifier,
    title: String,
    count: Int
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = YappTheme.typography.caption1Medium, color = YappTheme.colorScheme.labelAlternative)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "$count", style = YappTheme.typography.label1NormalBold)
    }
}

@Preview(showBackground = true)
@Composable
private fun StatusItemPreview() {
    YappTheme {
        Row {
            StatusItem(title = "출석", count = 12)
            StatusItem(title = "지각", count = 12)
            StatusItem(title = "결석", count = 12)
            StatusItem(title = "지각 면제권", count = 12)
        }
    }
}
