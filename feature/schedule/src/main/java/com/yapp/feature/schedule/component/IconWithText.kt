package com.yapp.feature.schedule.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Composable
internal fun IconWithText(
    iconResId: Int,
    text: String,
    contentDescription: String? = null
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = contentDescription,
            tint = Color.Unspecified
        )

        Text(
            text = text,
            style = YappTheme.typography.caption1Regular,
            color = YappTheme.colorScheme.labelAlternative
        )
    }
}