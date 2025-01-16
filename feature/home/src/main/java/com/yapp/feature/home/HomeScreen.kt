package com.yapp.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.text.YappTextAssistiveButtonSmall
import com.yapp.core.designsystem.theme.YappTheme


@Composable
fun HomeScreen (
){
    YappTheme {
        Column(
            modifier = Modifier.windowInsetsPadding(WindowInsets.safeContent),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappTextAssistiveButtonSmall(
                text = "Home",
                enable = true,
                onClick = {}
            )
        }
    }
}

