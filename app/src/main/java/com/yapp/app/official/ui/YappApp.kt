package com.yapp.app.official.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yapp.app.official.navigation.YappNavHost


@Composable
fun YappApp(navigator: NavigatorState) {
    Scaffold{ padding ->
        YappNavHost(navigator, modifier = Modifier.padding(padding))
    }
}

