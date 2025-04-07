package com.yapp.feature.home.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.outlined.YappOutlinedAssistiveButtonMedium
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.NoticeItem
import com.yapp.core.ui.component.NoticeLoadingItem
import com.yapp.core.ui.extension.borderBottom
import com.yapp.feature.home.R
import com.yapp.model.NoticeList

@Composable
fun NoticeSection(
    noticeInfo: NoticeList?,
    onNoticeDetailClick: (String) -> Unit,
    onMoreButtonClick: () -> Unit,
) {
    HomeSectionBackground {
        Text(
            text = stringResource(R.string.home_notice_title),
            color = YappTheme.colorScheme.labelNormal,
            style = YappTheme.typography.headline1Bold
        )
        Spacer(Modifier.height(8.dp))
        if (noticeInfo == null) {
            repeat(3) {
                NoticeLoadingItem(
                    modifier = if (it == 2) {
                        Modifier
                    } else {
                        Modifier.borderBottom(
                            color = YappTheme.colorScheme.lineNormalAlternative
                        )
                    }
                )
            }
        }else{
            repeat(minOf(3, noticeInfo.notices.size)) {
                val isLastItem = it == 2
                NoticeItem(
                    modifier = if (isLastItem) {
                        Modifier
                    } else {
                        Modifier.borderBottom(
                            color = YappTheme.colorScheme.lineNormalAlternative
                        )
                    },
                    noticeInfo = noticeInfo.notices[it],
                    onClick = { onNoticeDetailClick(noticeInfo.notices[it].id)}
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        YappOutlinedAssistiveButtonMedium(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.home_notice_more_button),
            enable = true,
            onClick = { onMoreButtonClick() },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoticeSectionPreview() {
    YappTheme {
    }
}
