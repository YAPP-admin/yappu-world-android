package com.yapp.feature.notice.noticedetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.chip.ChipColorType
import com.yapp.core.designsystem.component.chip.YappChipSmall
import com.yapp.core.designsystem.component.gradient.GradientBottom
import com.yapp.core.designsystem.component.header.YappHeaderActionbar
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.notice.R
import com.yapp.feature.notice.noticedetail.component.NoticeDetailLoading
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun NoticeDetailRoute(
    viewModel: NoticeDetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    noticeId: String,
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    viewModel.store.sideEffects.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            NoticeDetailSideEffect.NavigateToBack -> navigateBack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.store.onIntent(NoticeDetailIntent.EnterScreen(noticeId))
    }

    NoticeDetailScreen(
        uiState = uiState,
        onIntent = { viewModel.store.onIntent(it) }
    )
}

@Composable
fun NoticeDetailScreen(
    uiState: NoticeDetailState,
    onIntent: (NoticeDetailIntent) -> Unit = {},
) {
    var isRowVisible by remember { mutableStateOf(true) }
    val density = LocalDensity.current

    YappBackground {
        Column {
            YappHeaderActionbar(
                title = stringResource(R.string.notice_screen_header_title),
                leftIcon = com.yapp.core.designsystem.R.drawable.icon_chevron_left,
                contentDescription = stringResource(R.string.notice_screen_header_back_icon_content_description),
                onClickLeftIcon = {
                    onIntent(NoticeDetailIntent.ClickBackButton)
                },
            )
            if (uiState.isLoadingNotice) {
                NoticeDetailLoading()
            } else {
                BoxWithConstraints {
                    val maxHeight = maxHeight
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 20.dp,
                                end = 20.dp,
                                top = 16.dp
                            )
                            .verticalScroll(rememberScrollState()),
                    ) {
                        Text(
                            text = uiState.notice.title,
                            style = YappTheme.typography.headline1Bold,
                            color = YappTheme.colorScheme.labelNormal,
                        )

                        Spacer(Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.onGloballyPositioned { coordinates ->
                                val rowBottom = coordinates.boundsInWindow().bottom
                                val screenHeight = density.run { maxHeight.toPx() }
                                isRowVisible = rowBottom > 0 && rowBottom < screenHeight
                            }
                        ) {
                            YappChipSmall(
                                text = uiState.notice.noticeType.displayValue,
                                colorType = ChipColorType.Gray,
                                isFill = false
                            )

                            Text(
                                text = uiState.notice.writerLabel,
                                style = YappTheme.typography.caption1Regular,
                                color = YappTheme.colorScheme.labelAssistive,
                            )

                            Text(
                                text = "∙",
                                style = YappTheme.typography.caption1Regular,
                                color = YappTheme.colorScheme.labelAssistive,
                            )

                            Text(
                                text = uiState.notice.createdAt,
                                style = YappTheme.typography.caption1Regular,
                                color = YappTheme.colorScheme.labelAssistive,
                            )
                        }

                        Spacer(Modifier.height(24.dp))

                        MarkdownText(
                            style = YappTheme.typography.body2ReadingRegular,
                            markdown = runCatching { uiState.notice.content }.getOrDefault("내용을 불러올 수 없습니다."),
                        )
                    }

                    if (isRowVisible.not()) {
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
    }
}

@Preview
@Composable
private fun NoticeDetailScreenPreview() {
    YappTheme {
        NoticeDetailRoute(
            navigateBack = {},
            noticeId = ""
        )
    }
}
