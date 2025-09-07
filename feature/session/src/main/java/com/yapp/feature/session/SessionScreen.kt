package com.yapp.feature.session

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.button.icons.YappOutlinedIconButtonSmall
import com.yapp.core.designsystem.component.chip.ChipColorType
import com.yapp.core.designsystem.component.chip.YappChipLarge
import com.yapp.core.designsystem.component.gradient.GradientBottom
import com.yapp.core.designsystem.component.gradient.GradientTop
import com.yapp.core.designsystem.component.header.YappHeaderActionbar
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.NoticeItem
import com.yapp.core.ui.component.YappBackground
import com.yapp.model.NoticeInfo
import com.yapp.model.NoticeType
import com.yapp.model.SessionProgressPhase
import com.yapp.core.designsystem.R as DesignR

@Composable
internal fun SessionRoute(
    onBack: (() -> Unit)? = null,
    viewModel: SessionViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.store.onIntent(SessionIntent.EnterSessionScreen)
    }
    val state by viewModel.store.uiState.collectAsStateWithLifecycle()
    SessionScreen(
        state = state,
        onBack = onBack,
    )
}

@Composable
fun SessionScreen(
    state: SessionState,
    onBack: (() -> Unit)? = null,
) {
    val scrollState = rememberScrollState()
    val showGradientBottom by remember {
        derivedStateOf { scrollState.canScrollBackward }
    }

    YappBackground {
        Box {
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                // Header
                YappHeaderActionbar(
                    leftIcon = DesignR.drawable.icon_chevron_left,
                    contentDescription = "뒤로가기 버튼",
                    onClickLeftIcon = onBack,
                    title = "",
                )

                // Title Block
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 16.dp)
                ) {
                    YappChipLarge(
                        text = SessionProgressPhase.PENDING.title,
                        colorType = ChipColorType.Gray,
                        isFill = true,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "2차 데모데이",
                        style = YappTheme.typography.title2Bold,
                        color = YappTheme.colorScheme.labelNormal,
                        maxLines = 2,
                    )

                    Spacer(Modifier.height(8.dp))

                    // Date & Time
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = DesignR.drawable.icon_time),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = YappTheme.colorScheme.labelAlternative,
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "2025. 02. 15 (토) / 오후 6시 - 오후 8시",
                            style = YappTheme.typography.label1NormalRegular,
                            color = YappTheme.colorScheme.labelAlternative,
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    // Place name
                    Row {
                        Icon(
                            painter = painterResource(id = DesignR.drawable.icon_location),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = YappTheme.colorScheme.labelAlternative,
                        )
                        Spacer(Modifier.width(8.dp))

                        Column {
                            Text(
                                text = "서울 창업허브",
                                style = YappTheme.typography.label1NormalRegular,
                                color = YappTheme.colorScheme.labelAlternative,
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "서울 마포구 백범로31길 21 서울창업허브 서울복지타운",
                                style = YappTheme.typography.caption1Regular,
                                color = YappTheme.colorScheme.labelAssistive,
                            )
                            Spacer(Modifier.height(8.dp))

                            // Action buttons (placeholders)
                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                Image(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .yappClickable(onClick = {}),
                                    painter = painterResource(R.drawable.image_kakao_map),
                                    contentDescription = "카카오맵으로 이동"
                                )
                                Image(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .yappClickable(onClick = {}),
                                    painter = painterResource(R.drawable.image_naver_map),
                                    contentDescription = "네이버 지도로 이동"
                                )
                                YappOutlinedIconButtonSmall(
                                    resourceId = R.drawable.icon_copy,
                                    contentDescription = "주소 복사하기",
                                    onClick = {},
                                )
                            }

                            Spacer(Modifier.height(8.dp))

                            // Map placeholder
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(YappTheme.colorScheme.skeleton)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))
                HorizontalDivider(
                    thickness = 12.dp,
                    color = YappTheme.colorScheme.lineNormalAlternative
                )

                // Notice section
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Spacer(Modifier.height(24.dp))
                    Text(
                        text = "공지사항",
                        style = YappTheme.typography.title3Bold,
                        color = YappTheme.colorScheme.labelNormal,
                    )

                    Spacer(Modifier.height(8.dp))
                    DummyNotices().forEachIndexed { index, notice ->
                        NoticeItem(
                            noticeInfo = notice,
                            onClick = {},
                        )
                        if (index != DummyNotices().lastIndex) {
                            Spacer(Modifier.height(8.dp))
                            HorizontalDivider(color = YappTheme.colorScheme.lineNormalAlternative)
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                }
            }

            if (showGradientBottom) {
                GradientBottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    color = YappTheme.colorScheme.staticWhite
                )
            }

            GradientTop(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .align(Alignment.BottomCenter),
                color = YappTheme.colorScheme.staticWhite
            )
        }
    }
}

private fun DummyNotices(): List<NoticeInfo> = listOf(
    NoticeInfo(
        id = "1",
        writerName = "홍길동",
        writerId = "1",
        writerPosition = "운영진",
        writerGeneration = 20,
        createdAt = "2023-08-13",
        title = "심장 건강을 책임지는 스마트 워치, 심박수 감시와 예...",
        content = "한반도의 경제 협력이 새로운 국면을 맞이하며 남북 간 첫 연합 기업이 설립되었습니다.",
        noticeType = NoticeType.SESSION
    ),
    NoticeInfo(
        id = "2",
        writerName = "홍길동",
        writerId = "2",
        writerPosition = "운영진",
        writerGeneration = 20,
        createdAt = "2023-08-13",
        title = "심장 건강을 책임지는 스마트 워치, 심박수 감시와 예...",
        content = "한반도의 경제 협력이 새로운 국면을 맞이하며 남북 간 첫 연합 기업이 설립되었습니다.",
        noticeType = NoticeType.SESSION
    ),
    NoticeInfo(
        id = "3",
        writerName = "홍길동",
        writerId = "3",
        writerPosition = "운영진",
        writerGeneration = 20,
        createdAt = "2023-08-13",
        title = "심장 건강을 책임지는 스마트 워치, 심박수 감시와 예...",
        content = "한반도의 경제 협력이 새로운 국면을 맞이하며 남북 간 첫 연합 기업이 설립되었습니다.",
        noticeType = NoticeType.SESSION
    )
)

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SessionScreenPreview() {
    YappTheme {
        SessionScreen(state = SessionState())
    }
}
