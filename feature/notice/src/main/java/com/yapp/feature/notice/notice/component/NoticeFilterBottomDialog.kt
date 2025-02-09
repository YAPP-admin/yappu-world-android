package com.yapp.feature.notice.notice.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonLarge
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.BottomDialog
import com.yapp.feature.notice.R

@Composable
fun NoticeFilterBottomDialog() {
    BottomDialog(
        onDismissRequest = {}
    ) {
        NoticeFilterBottomDialogContent()
    }
}

@Composable
private fun NoticeFilterBottomDialogContent() {
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            text = stringResource(R.string.notice_filter_bottom_dialog_title),
            style = YappTheme.typography.headline1Bold,
            color = YappTheme.colorScheme.labelNormal
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.notice_filter_bottom_dialog_expose),
            style = YappTheme.typography.label1NormalBold,
            color = YappTheme.colorScheme.labelNormal
        )

        Spacer(Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NoticeFilterButton(
                name = "ALL",
                checked = true,
                onCheckedChange = {}
            )

            NoticeFilterButton(
                name = "전체공지",
                checked = false,
                onCheckedChange = {}
            )

            NoticeFilterButton(
                name = "정회원",
                checked = false,
                onCheckedChange = {}
            )

            NoticeFilterButton(
                name = "활동회원",
                checked = false,
                onCheckedChange = {}
            )
        }

        Spacer(Modifier.height(24.dp))

        YappSolidPrimaryButtonLarge(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.notice_filter_bottom_dialog_button_text),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoticeFilterBottomDialogPreview() {
    YappTheme {
        NoticeFilterBottomDialogContent()
    }
}