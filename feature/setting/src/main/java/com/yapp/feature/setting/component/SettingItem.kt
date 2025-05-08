package com.yapp.feature.setting.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.R
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun SettingItemLarge(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (() -> Unit)? = null,
    slot: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .yappClickable(
                runIf = onClick != null,
                onClick = onClick
            )
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = YappTheme.typography.body1NormalBold,
            color = YappTheme.colorScheme.labelNormal
        )

        slot?.invoke()
    }
}

@Composable
fun SettingItemMedium(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (() -> Unit)? = null,
    slot: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .yappClickable(
                runIf = onClick != null,
                onClick = onClick
            )
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = YappTheme.typography.label1NormalBold,
            color = YappTheme.colorScheme.labelNormal
        )

        slot?.invoke()
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingItemLargePreview() {
    YappTheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            SettingItemLarge(
                text = "Notification",
                onClick = { /* do something */ }
            )

            Spacer(Modifier.height(8.dp))

            SettingItemLarge(
                text = "Version",
                slot = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_chevron_right),
                        contentDescription = null,
                        tint = YappTheme.colorScheme.labelAssistive,
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingItemMediumPreview() {
    YappTheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            SettingItemMedium(
                text = "Privacy Policy",
                onClick = { /* do something */ },
                slot = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_chevron_right),
                        contentDescription = null,
                        tint = YappTheme.colorScheme.labelAssistive,
                    )
                }
            )

            Spacer(Modifier.height(8.dp))

            SettingItemMedium(
                text = "Terms of Service"
            )
        }
    }
}