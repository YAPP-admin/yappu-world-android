package com.yapp.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun YappSkeleton(
    modifier: Modifier,
    radius: Double = 15.0,
) {
    Box(
        modifier
            .background(YappTheme.colorScheme.skeleton, shape = RoundedCornerShape(radius.dp))
    )
}