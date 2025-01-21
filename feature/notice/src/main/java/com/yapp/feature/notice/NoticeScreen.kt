package com.yapp.feature.notice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.text.YappTextAssistiveButtonSmall

@Composable
fun NoticeScreen (
){
    Column(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeContent),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        YappTextAssistiveButtonSmall(
            text = "Notice",
            enable = true,
            onClick = {}
        )
    }
}

