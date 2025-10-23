package com.yapp.feature.session

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import com.yapp.core.ui.component.YappSkeleton
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.Alignment
import com.naver.maps.geometry.LatLng
import com.yapp.core.ui.extension.collectWithLifecycle
import java.net.URLEncoder
import com.yapp.core.designsystem.R as DesignR
import androidx.core.net.toUri

@Composable
internal fun SessionRoute(
    navigateToBack: () -> Unit = {},
    navigateToLogin: () -> Unit = {},
    navigateToNoticeDetail: (String) -> Unit = {},
    handleException: (Throwable) -> Unit = {},
    viewModel: SessionViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        // 애니메이션이 완료된 후 API 호출 시작 (300ms 애니메이션 + 약간의 여유)
        kotlinx.coroutines.delay(350)
        viewModel.store.onIntent(SessionIntent.EnterSessionScreen)
    }
    val state by viewModel.store.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    viewModel.store.sideEffects.collectWithLifecycle { effect ->
        when (effect) {
            SessionSideEffect.NavigateToLogin -> navigateToLogin()
            is SessionSideEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
            is SessionSideEffect.HandleException -> handleException(effect.throwable)
            is SessionSideEffect.NavigateToNoticeDetail -> navigateToNoticeDetail(effect.noticeId)
            is SessionSideEffect.OpenKakaoMap -> {
                openKakaoMap(context, effect.name, effect.latitude, effect.longitude)
            }
            is SessionSideEffect.OpenNaverMap -> {
                openNaverMap(context, effect.latitude, effect.longitude, effect.name)
            }
            is SessionSideEffect.CopyAddressToClipboard -> {
                copyAddressToClipboard(context, effect.address)
            }
        }
    }

    SessionScreen(
        state = state,
        onBack = navigateToBack,
        onIntent = { viewModel.store.onIntent(it) },
    )
}

@Composable
fun SessionScreen(
    state: SessionState,
    onBack: (() -> Unit)? = null,
    onIntent: (SessionIntent) -> Unit = {},
) {
    val sessionDetail = state.sessionDetail
    val location = remember(sessionDetail) {
        sessionDetail?.let { LatLng(it.latitude, it.longitude) }
    }

    val scrollState = rememberScrollState()
    val showGradientBottom by remember {
        derivedStateOf { scrollState.canScrollBackward }
    }

    // 타이틀 초기 위치와 헤더 높이 저장
    var titleInitialBottom by remember { mutableFloatStateOf(0f) }
    var headerHeight by remember { mutableFloatStateOf(0f) }
    var isTitlePositioned by remember { mutableStateOf(false) }

    // 타이틀이 완전히 가려졌는지 확인
    val showTitleInHeader by remember {
        derivedStateOf {
            isTitlePositioned && scrollState.value > titleInitialBottom
        }
    }

    val sessionTitle = sessionDetail?.title ?: ""

    YappBackground {
        Box {
            if (state.isLoading) {
                SessionSkeletonScreen()
            } else {
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(top = with(LocalDensity.current) { headerHeight.toDp() })
                ) {
                // Title Block
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 16.dp)
                ) {
                    sessionDetail?.progressPhase?.let { phase ->
                        YappChipLarge(
                            text = phase.title,
                            colorType = ChipColorType.Gray,
                            isFill = true,
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.onGloballyPositioned { coordinates ->
                            if (!isTitlePositioned && headerHeight != 0f) {
                                val bounds = coordinates.boundsInParent()
                                // 타이틀의 상단 위치를 저장 (타이틀이 완전히 사라질 때까지 기다림)
                                titleInitialBottom = bounds.bottom
                                isTitlePositioned = true
                            }
                        },
                        text = sessionTitle,
                        style = YappTheme.typography.title2Bold,
                        color = YappTheme.colorScheme.labelNormal,
                        maxLines = 2,
                    )

                    Spacer(Modifier.height(8.dp))

                    // Date & Time
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_calendar),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = YappTheme.colorScheme.labelAlternative,
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = state.sessionDateTime,
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
                                text = sessionDetail?.place ?: "",
                                style = YappTheme.typography.label1NormalRegular,
                                color = YappTheme.colorScheme.labelAlternative,
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = sessionDetail?.address ?: "",
                                style = YappTheme.typography.caption1Regular,
                                color = YappTheme.colorScheme.labelAssistive,
                            )
                            Spacer(Modifier.height(8.dp))

                            // Action buttons (placeholders)
                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                if (location != null && sessionDetail != null) {
                                    Image(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .yappClickable(onClick = {
                                                onIntent(
                                                    SessionIntent.ClickKakaoMap(
                                                        name = sessionDetail.place,
                                                        latitude = location.latitude,
                                                        longitude = location.longitude
                                                    )
                                                )
                                            }),
                                        painter = painterResource(R.drawable.image_kakao_map),
                                        contentDescription = "카카오맵으로 이동"
                                    )
                                    Image(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .yappClickable(onClick = {
                                                onIntent(
                                                    SessionIntent.ClickNaverMap(
                                                        latitude = location.latitude,
                                                        longitude = location.longitude,
                                                        name = sessionDetail.place
                                                    )
                                                )
                                            }),
                                        painter = painterResource(R.drawable.image_naver_map),
                                        contentDescription = "네이버 지도로 이동"
                                    )
                                    YappOutlinedIconButtonSmall(
                                        resourceId = R.drawable.icon_copy,
                                        contentDescription = "주소 복사하기",
                                        onClick = {
                                            onIntent(SessionIntent.ClickCopyAddress(sessionDetail.address))
                                        },
                                    )
                                }
                            }

                            Spacer(Modifier.height(8.dp))

                            location?.let { loc ->
                                SessionNaverMap(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    center = loc,
                                    locationName = sessionDetail?.place,
                                )
                            }
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
                    sessionDetail?.notices?.forEachIndexed { index, notice ->
                        NoticeItem(
                            noticeInfo = notice,
                            onClick = { onIntent(SessionIntent.ClickNoticeItem(notice.id)) },
                        )
                        if (index != sessionDetail.notices.lastIndex) {
                            Spacer(Modifier.height(8.dp))
                            HorizontalDivider(color = YappTheme.colorScheme.lineNormalAlternative)
                        }
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }
            }


            GradientTop(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .align(Alignment.BottomCenter),
                color = YappTheme.colorScheme.staticWhite
            )

            Column(
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                YappHeaderActionbar(
                    modifier = Modifier
                        .background(color = YappTheme.colorScheme.backgroundElevatedNormal)
                        .onGloballyPositioned { coordinates ->
                            headerHeight = coordinates.size.height.toFloat()
                        },
                    leftIcon = DesignR.drawable.icon_chevron_left,
                    contentDescription = "뒤로가기 버튼",
                    onClickLeftIcon = onBack,
                    title = if (showTitleInHeader) sessionTitle else "",
                )

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
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SessionScreenPreview() {
    YappTheme {
        SessionScreen(state = SessionState())
    }
}

private fun openKakaoMap(context: Context, name: String, latitude: Double, longitude: Double) {
    // Use search scheme to show place with name
    val encodedName = try {
        URLEncoder.encode(name, Charsets.UTF_8.name())
    } catch (e: Exception) {
        name
    }
    val kakaoUri = "kakaomap://search?q=$encodedName&p=$latitude,$longitude".toUri()
    val intent = Intent(Intent.ACTION_VIEW, kakaoUri)
    // Prefer Kakao Map app explicitly if present
    intent.`package` = "net.daum.android.map"
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // Fallback to Kakao Map web with place name
        val web = Intent(
            Intent.ACTION_VIEW,
            "https://map.kakao.com/link/map/$encodedName,$latitude,$longitude".toUri()
        )
        context.startActivity(web)
    }
}

private fun openNaverMap(context: Context, latitude: Double, longitude: Double, name: String) {
    val encodedName = try {
        URLEncoder.encode(name, Charsets.UTF_8.name())
    } catch (e: Exception) {
        name
    }
    val naverUri = "nmap://search?query=$encodedName&appname=${context.packageName}".toUri()
    val intent = Intent(Intent.ACTION_VIEW, naverUri)
    intent.`package` = "com.nhn.android.nmap"
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // Fallback to Naver Map web (centered at coordinates or search by name)
        val webUrl =
            "https://map.naver.com/v5/search/$encodedName?c=$longitude,$latitude,16,0,0,0,dh"
        val web = Intent(Intent.ACTION_VIEW, webUrl.toUri())
        context.startActivity(web)
    }
}

@Composable
fun SessionSkeletonScreen() {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(16.dp))

        YappSkeleton(
            modifier = Modifier
                .width(80.dp)
                .height(32.dp),
            radius = 16.0
        )

        Spacer(Modifier.height(8.dp))

        YappSkeleton(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            radius = 8.0
        )

        Spacer(Modifier.height(8.dp))

        YappSkeleton(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(20.dp),
            radius = 4.0
        )

        Spacer(Modifier.height(8.dp))

        YappSkeleton(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            radius = 4.0
        )

        Spacer(Modifier.height(4.dp))

        YappSkeleton(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(16.dp),
            radius = 4.0
        )

        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            repeat(3) {
                YappSkeleton(
                    modifier = Modifier
                        .size(32.dp),
                    radius = 16.0
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        YappSkeleton(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            radius = 8.0
        )

        Spacer(Modifier.height(24.dp))

        YappSkeleton(
            modifier = Modifier
                .width(120.dp)
                .height(28.dp),
            radius = 4.0
        )

        Spacer(Modifier.height(16.dp))

        repeat(3) {
            YappSkeleton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                radius = 8.0
            )
            if (it < 2) {
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

private fun copyAddressToClipboard(context: Context, address: String) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("address", address)
    clipboardManager.setPrimaryClip(clip)
}
