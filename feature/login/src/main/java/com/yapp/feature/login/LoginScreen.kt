package com.yapp.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.text.YappTextAssistiveButtonSmall

@Composable
fun LoginScreen (
){
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        YappTextAssistiveButtonSmall(
            text = "Login",
            enable = true,
            onClick = {}
        )
    }
}

