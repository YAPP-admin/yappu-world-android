package com.yapp.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonLarge
import com.yapp.core.designsystem.theme.YappTheme


@Composable
fun HomeScreen (
){
    YappTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            YappSolidPrimaryButtonLarge(
                text = "Label",
                enable = true,
                onClick = {}
            )
            YappSolidPrimaryButtonLarge(
                text = "Label",
                enable = false,
                onClick = {}
            )
        }
    }
}

