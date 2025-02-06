package com.yapp.feature.home.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.outlined.YappOutlinedAssistiveButtonMedium
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.NoticeInfo
import com.yapp.core.ui.component.NoticeItem
import com.yapp.feature.home.R

@Composable
fun NoticeSection(
    noticeInfo: List<NoticeInfo>,
    onMoreButtonClick : () -> Unit
){
    HomeSectionBackground {
        Text(
            text = stringResource(R.string.home_notice_title),
            color = YappTheme.colorScheme.labelNormal,
            style = YappTheme.typography.headline1Bold
        )
        Spacer(Modifier.height(8.dp))
        repeat(3) {
            val isLastItem = it == 2
            NoticeItem(noticeInfo = noticeInfo[it], isLastItem = isLastItem)
        }
        Spacer(Modifier.height(8.dp))
        YappOutlinedAssistiveButtonMedium(modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.home_notice_more_button),
            enable = true,
            onClick = {onMoreButtonClick()}
        )
    }
}