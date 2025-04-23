package com.yapp.core.designsystem.component.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.R
import com.yapp.core.designsystem.extension.yappClickable

@Composable
fun YappDefaultHeader(
    modifier: Modifier = Modifier,
    onClickRightIcon: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(R.drawable.icon_yapp),
            contentDescription = "logo",
        )
        Spacer(Modifier.width(8.dp))
        Image(
            painter = painterResource(R.drawable.icon_yapp_text),
            contentDescription = "logo_text",
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                modifier = Modifier.yappClickable(onClick = onClickRightIcon),
                painter = painterResource(R.drawable.icon_setting),
                contentDescription = "setting",

            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun YappDefaultHeaderPreview() {
    YappDefaultHeader()
}