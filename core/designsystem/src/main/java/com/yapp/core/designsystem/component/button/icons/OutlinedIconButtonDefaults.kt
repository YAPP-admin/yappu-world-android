package com.yapp.core.designsystem.component.button.icons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Immutable
object OutlinedIconButtonDefaults {
    val sizeSmall = 32.dp
    val iconSizeSmall = 18.dp

    val borderColor
        @Composable
        get() = YappTheme.colorScheme.lineNormalNormal
}