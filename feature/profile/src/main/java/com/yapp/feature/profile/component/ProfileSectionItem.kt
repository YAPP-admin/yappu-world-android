package com.yapp.feature.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.designsystem.R as coreDesignR

@Composable
internal fun ProfileSectionItem(
    modifier: Modifier = Modifier,
    title: String,
    slot: @Composable (() -> Unit)? = null,
    onClickItem: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .yappClickable(onClick = onClickItem)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title, color = YappTheme.colorScheme.labelNeutral, style = YappTheme.typography.headline1Bold
        )
        slot?.invoke()
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileSectionItemPreview() {
    YappTheme {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            ProfileSectionItem(
                onClickItem = {},
                title = "이용문의"
            )
            ProfileSectionItem(
                onClickItem = {},
                title = "이용문의",
                slot = {
                    Icon(
                        painter = painterResource(id = coreDesignR.drawable.icon_chevron_right),
                        contentDescription = null,
                        tint = YappTheme.colorScheme.labelAssistive
                    )
                }
            )
        }
    }
}
