package com.yapp.feature.login.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun HorizontalDivider(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        androidx.compose.material3.HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = YappTheme.colorScheme.lineNormalNormal,
            thickness = 1.dp
        )
        Text(
            text = "또는",
            modifier = Modifier.padding(horizontal = 4.dp),
            color = YappTheme.colorScheme.labelAssistive,
            style = YappTheme.typography.caption1Medium
        )
        androidx.compose.material3.HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = YappTheme.colorScheme.lineNormalNormal,
            thickness = 1.dp
        )
    }
}