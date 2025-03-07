package com.yapp.feature.notice.notice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.chip.ChipColorType
import com.yapp.core.designsystem.component.gradient.GradientBottom
import com.yapp.core.designsystem.component.header.YappHeaderActionbarExpanded
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.NoticeInfo
import com.yapp.core.ui.component.NoticeItem
import com.yapp.core.ui.component.TagInfo
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.borderBottom
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.notice.R
import com.yapp.feature.notice.notice.component.NoticeCategoryButton

@Composable
fun NoticeRoute(
    viewModel: NoticeViewModel = hiltViewModel(),
    navigateToNoticeDetail: (String) -> Unit,
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    viewModel.store.sideEffects.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is NoticeSideEffect.NavigateToNoticeDetail -> navigateToNoticeDetail(sideEffect.noticeId)
        }
    }

    NoticeScreen(
        uiState = uiState,
        onIntent = { viewModel.store.onIntent(it) }
    )
}

@Composable
fun NoticeScreen(
    uiState: NoticeState = NoticeState(),
    onIntent: (NoticeIntent) -> Unit = {},
) {
    YappBackground {
        Column {
            YappHeaderActionbarExpanded(
                title = stringResource(R.string.notice_screen_header_title),
                leftIcon = com.yapp.core.designsystem.R.drawable.icon_chevron_left,
                contentDescription = stringResource(R.string.notice_screen_header_back_icon_content_description),
                onClickLeftIcon = {
                    onIntent(NoticeIntent.ClickBackButton)
                },
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    NoticeCategoryButton(
                        name = "전체",
                        checked = true,
                        onCheckedChange = {}
                    )

                    NoticeCategoryButton(
                        name = "운영",
                        checked = false,
                        onCheckedChange = {}
                    )

                    NoticeCategoryButton(
                        name = "세션",
                        checked = false,
                        onCheckedChange = {}
                    )
                }
            }

            Box {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    contentPadding = PaddingValues(vertical = 16.dp),
                ) {
                    items(count = 10) {
                        NoticeItem(
                            modifier = Modifier.borderBottom(
                                color = YappTheme.colorScheme.lineNormalAlternative
                            ),
                            noticeInfo = NoticeInfo(
                                tags = listOf(
                                    TagInfo("세션", ChipColorType.Main),
                                    TagInfo("정회원", ChipColorType.Sub)
                                ),
                                creationDate = "2023-08-13",
                                writer = "20기 홍길동",
                                title = "심장 건강을 책임지는 스마트 워치,심박수 감시와 예방 기능 탑재",
                                bodyText = "한반도의 경제 협력이 새로운 국면을 맞이하며 남북 간 첫 연합 기업이 설립되었습니다. 이 기업은 에너지, 통신, 제조 등 다양한 분야에서 남북 간 협력 모델을 제시하며 경제적 도약을 목표로 하고 있습니다. 특히, 이번 연합 기업은 지속 가능한 발전을 위한 친환경 기술과 공동 연구 개발을 핵심 과제로 삼고 있으며, 이를 통해 글로벌 시장에서도 경쟁력을 확보하고자 합니다. 전문가들은 이러한 경제 협력이 한반도뿐만 아니라 동아시아 전체의 경제적 안정과 성장에도 기여할 것으로 예상하고 있습니다."
                            ),
                            onClick = { onIntent(NoticeIntent.ClickNoticeItem("$it")) }
                        )
                    }
                }

                GradientBottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    color = YappTheme.colorScheme.staticWhite
                )
            }
        }
    }
}

@Preview
@Composable
private fun NoticeScreenPreview() {
    YappTheme {
        NoticeScreen()
    }
}
