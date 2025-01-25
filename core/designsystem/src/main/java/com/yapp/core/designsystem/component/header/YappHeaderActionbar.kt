package com.yapp.core.designsystem.component.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun YappHeaderActionbar(
    modifier: Modifier = Modifier,
    leftIcon: @Composable (() -> Unit)? = null,
    rightAction: @Composable (RowScope.() -> Unit)? = null,
    title: String? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        if (title != null) {
            Text(
                text = title,
                style = YappTheme.typography.heading1Bold,
                color = YappTheme.colorScheme.labelNormal,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            leftIcon?.invoke()

            rightAction?.invoke(this)
        }
    }
}

@Composable
fun HeaderActionbarExpanded(
    modifier: Modifier = Modifier,
    leftIcon: @Composable (() -> Unit)? = null,
    rightAction: @Composable (RowScope.() -> Unit)? = null,
    title: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            leftIcon?.invoke()

            rightAction?.invoke(this)
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = title,
            style = YappTheme.typography.title3Bold,
            color = YappTheme.colorScheme.labelNormal,
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun HeaderActionbarPreview() {
    YappTheme {
        YappHeaderActionbar(
            leftIcon = {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            },
            title = "Title"
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun HeaderActionbarExpandedPreview() {
    YappTheme {
        HeaderActionbarExpanded(
            leftIcon = {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            },
            title = "Title"
        )
    }
}