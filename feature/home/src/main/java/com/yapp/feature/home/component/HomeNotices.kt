package com.yapp.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.outlined.YappOutlinedAssistiveButtonMedium
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.NoticeItem
import com.yapp.feature.home.R
import com.yapp.model.NoticeInfo
import com.yapp.model.NoticeType

@Composable
internal fun HomeNotices(
    modifier: Modifier = Modifier,
    notices: List<NoticeInfo>,
    onClickMore: () -> Unit = { }
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = YappTheme.colorScheme.staticWhite)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))


        Text(
            text = stringResource(id = R.string.home_notice_title),
            style = YappTheme.typography.heading2Bold,
            color = YappTheme.colorScheme.labelNormal
        )


        Spacer(modifier = Modifier.height(8.dp))

        notices.forEachIndexed { index, notice ->
            NoticeItem(noticeInfo = notice)

            if (index != notices.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = YappTheme.colorScheme.lineNormalAlternative
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        YappOutlinedAssistiveButtonMedium(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.home_notice_more_button),
            onClick = onClickMore
        )
    }
}

@Preview
@Composable
private fun HomeRecentAttendanceHistoryItemPreview() {
    YappTheme {
        HomeNotices(
            notices = listOf(NoticeInfo(
                id = "111",
                writerName = "홍길동",
                writerId = "asd",
                writerPosition = "",
                writerGeneration = 22,
                createdAt = "2025-09-14",
                title = "테스트 타이틀",
                content = "이것은 내용입니다.",
                noticeType = NoticeType.ALL,
            )),
        )
    }
}