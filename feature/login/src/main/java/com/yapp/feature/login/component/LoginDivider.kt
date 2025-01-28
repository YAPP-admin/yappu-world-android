package com.yapp.feature.login.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.feature.login.R

@Composable
fun LoginDivider(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = YappTheme.colorScheme.lineNormalNormal,
            thickness = 1.dp
        )
        Text(
            text = stringResource(R.string.login_divider),
            modifier = Modifier.padding(horizontal = 4.dp),
            color = YappTheme.colorScheme.labelAssistive,
            style = YappTheme.typography.caption1Medium
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = YappTheme.colorScheme.lineNormalNormal,
            thickness = 1.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    YappTheme {
        LoginDivider()
    }
}