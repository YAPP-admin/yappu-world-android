package com.yapp.feature.notice.notice.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.yapp.core.designsystem.component.button.outlined.YappOutlinedAssistiveButtonXSmall
import com.yapp.core.designsystem.component.button.outlined.YappOutlinedPrimaryButtonXSmall
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonXSmall
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun NoticeFilterButton(
    name: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    if (checked) {
        YappSolidPrimaryButtonXSmall(
            text = name,
            onClick = {
                onCheckedChange(false)
            }
        )
    } else {
        YappOutlinedPrimaryButtonXSmall(
            text = name,
            onClick = {
                onCheckedChange(true)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoticeFilterPreview() {
    var checked by remember { mutableStateOf(false) }
    YappTheme {
        NoticeFilterButton(
            name = "전체",
            checked = checked,
            onCheckedChange = {
                checked = it
            }
        )
    }
}