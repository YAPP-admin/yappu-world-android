package com.yapp.feature.notice.notice.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.yapp.core.designsystem.component.button.outlined.YappOutlinedAssistiveButtonXSmall
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonXSmall
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun NoticeCategoryButton(
    name: String,
    checked: Boolean,
    onCheckedChange: () -> Unit,
) {
    if (checked) {
        YappSolidPrimaryButtonXSmall(
            text = name,
            onClick = {
                onCheckedChange()
            }
        )
    } else {
        YappOutlinedAssistiveButtonXSmall(
            text = name,
            onClick = {
                onCheckedChange()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoticeCategoryPreview() {
    var checked by remember { mutableStateOf(false) }
    YappTheme {
        NoticeCategoryButton(
            name = "전체",
            checked = checked,
            onCheckedChange = {}
        )
    }
}