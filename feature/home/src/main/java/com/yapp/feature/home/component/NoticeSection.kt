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
import com.yapp.core.designsystem.component.chip.ChipColorType
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.NoticeInfo
import com.yapp.core.ui.component.NoticeItem
import com.yapp.core.ui.component.TagInfo
import com.yapp.feature.home.R

@Composable
fun NoticeSection(
    noticeInfo: List<NoticeInfo>,
    onMoreButtonClick: () -> Unit,
) {
    HomeSectionBackground {
        Text(
            text = stringResource(R.string.home_notice_title),
            color = YappTheme.colorScheme.labelNormal,
            style = YappTheme.typography.headline1Bold
        )
        Spacer(Modifier.height(8.dp))
        repeat(minOf(3, noticeInfo.size)) {
            val isLastItem = it == 2
            NoticeItem(noticeInfo = noticeInfo[it], isLastItem = isLastItem)
        }
        Spacer(Modifier.height(8.dp))
        YappOutlinedAssistiveButtonMedium(modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.home_notice_more_button),
            enable = true,
            onClick = { onMoreButtonClick() })
    }
}

@Preview(showBackground = true)
@Composable
private fun NoticeSectionPreview() {
    YappTheme {
        NoticeSection(noticeInfo = listOf(
            NoticeInfo(
                tags = listOf(
                    TagInfo("운영", ChipColorType.Gray), TagInfo("정회원", ChipColorType.Sub)
                ),
                creationDate = "2023-08-13",
                writer = "20기 홍길동",
                title = "심장 건강을 책임지는 스마트 워치,심박수 감시와 예방 기능 탑재",
                bodyText = "한반도의 경제 협력이 새로운 국면을 맞이하며 남북 간 첫 연합 기업이 설립되었습니다. 이 기업은 에너지, 통신, 제조 등 다양한 분야에서 남북 간 협력 모델을 제시하며 경제적 도약을 목표로 하고 있습니다. 특히, 이번 연합 기업은 지속 가능한 발전을 위한 친환경 기술과 공동 연구 개발을 핵심 과제로 삼고 있으며, 이를 통해 글로벌 시장에서도 경쟁력을 확보하고자 합니다. 전문가들은 이러한 경제 협력이 한반도뿐만 아니라 동아시아 전체의 경제적 안정과 성장에도 기여할 것으로 예상하고 있습니다."
            )
        ), {})
    }
}
