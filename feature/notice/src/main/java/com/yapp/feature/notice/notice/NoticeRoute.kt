package com.yapp.feature.notice.notice

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.gradient.GradientBottom
import com.yapp.core.designsystem.component.header.YappHeaderActionbarExpanded
import com.yapp.core.designsystem.component.header.YappHeaderTitle
import com.yapp.core.designsystem.extension.OnBottomReached
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.NoticeItem
import com.yapp.core.ui.component.NoticeLoadingItem
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.borderBottom
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.notice.R
import com.yapp.feature.notice.notice.component.NoticeCategoryButton
import com.yapp.model.NoticeType

@Composable
fun NoticeRoute(
    viewModel: NoticeViewModel = hiltViewModel(),
    navigateToNoticeDetail: (String) -> Unit,
    handleException: (Throwable) -> Unit,
    navigateToLogin: () -> Unit,
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    viewModel.store.sideEffects.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is NoticeSideEffect.NavigateToNoticeDetail -> { navigateToNoticeDetail(sideEffect.noticeId)}
            is NoticeSideEffect.HandleException -> handleException(sideEffect.exception)
            NoticeSideEffect.NavigateToLogin -> navigateToLogin()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.store.onIntent(NoticeIntent.EnterNoticeScreen)
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
    val lazyScrollState = rememberLazyListState()
    val showGradientBottom by remember {
        derivedStateOf { lazyScrollState.canScrollBackward }
    }

    YappBackground {
        Column {
            YappHeaderTitle(
                title = stringResource(R.string.notice_screen_header_title),
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
                        name = NoticeType.ALL.displayValue,
                        checked = uiState.isAll,
                        onCheckedChange = { onIntent(NoticeIntent.ClickNoticeType(NoticeType.ALL)) }
                    )
                    NoticeCategoryButton(
                        name = NoticeType.OPERATION.displayValue,
                        checked = uiState.isOperation,
                        onCheckedChange = { onIntent(NoticeIntent.ClickNoticeType(NoticeType.OPERATION)) }
                    )

                    NoticeCategoryButton(
                        name = NoticeType.SESSION.displayValue,
                        checked = uiState.isSession,
                        onCheckedChange = { onIntent(NoticeIntent.ClickNoticeType(NoticeType.SESSION)) }
                    )
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                if (uiState.isNoticeEmpty) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.illust_emtpy_notices),
                            contentDescription = null,
                        )
                        Spacer(Modifier.height(32.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.notice_list_empty_text),
                            color = YappTheme.colorScheme.labelAlternative,
                            style = YappTheme.typography.label1NormalRegular,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        state = lazyScrollState
                    ) {
                        if (uiState.isNoticesLoading) {
                            items(count = 7) {
                                NoticeLoadingItem(
                                    Modifier.borderBottom(
                                        color = YappTheme.colorScheme.lineNormalAlternative
                                    )
                                )
                            }
                        } else {
                            items(uiState.notices.notices) {
                                NoticeItem(
                                    modifier = Modifier.borderBottom(
                                        color = YappTheme.colorScheme.lineNormalAlternative
                                    ),
                                    noticeInfo = it,
                                    onClick = { onIntent(NoticeIntent.ClickNoticeItem(it.id)) }
                                )
                            }
                        }
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
            }
        }

    }

    lazyScrollState.OnBottomReached {
        onIntent(NoticeIntent.LoadMoreNoticeItem)
    }
}

@Preview
@Composable
private fun NoticeScreenPreview() {
    YappTheme {
        NoticeScreen()
    }
}
