package com.yapp.core.designsystem.component.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.yapp.core.designsystem.component.button.outlined.YappOutlinedSecondaryButtonXLarge
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonXLarge
import com.yapp.core.designsystem.theme.YappTheme

@Composable
fun YappAlertDialog(
    onDismissRequest: () -> Unit,
    title: String? = null,
    body: String? = null,
    actionButtonText: String? = null,
    recommendActionButtonText: String? = null,
    onActionButtonClick: (() -> Unit)? = null,
    onRecommendActionButtonClick: (() -> Unit)? = null,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = YappTheme.colorScheme.backgroundNormalNormal,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(16.dp),
        ) {
            title?.let {
                Text(
                    text = it,
                    style = YappTheme.typography.headline1Bold,
                    color = YappTheme.colorScheme.labelNormal,
                )
            }

            if (title != null && body != null) {
                Spacer(modifier = Modifier.height(8.dp))
            }

            body?.let {
                Text(
                    text = it,
                    style = YappTheme.typography.label1ReadingRegular,
                    color = YappTheme.colorScheme.labelNeutral,
                )
            }

            if (actionButtonText != null || recommendActionButtonText != null) {
                Spacer(modifier = Modifier.height(16.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                actionButtonText?.let {
                    YappOutlinedSecondaryButtonXLarge(
                        modifier = Modifier.weight(1f),
                        text = it,
                        onClick = onActionButtonClick ?: {}
                    )
                }

                recommendActionButtonText?.let {
                    YappSolidPrimaryButtonXLarge(
                        modifier = Modifier.weight(1f),
                        text = it,
                        onClick = onRecommendActionButtonClick ?: {}
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun YappAlertDialogPreview() {
    YappTheme {
        YappAlertDialog(
            title = "Title",
            body = "body",
            actionButtonText = "Action",
            recommendActionButtonText = "Recom",
            onDismissRequest = {}
        )
    }
}