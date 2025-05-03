package com.yapp.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.SessionChip
import com.yapp.feature.home.HomeState
import com.yapp.feature.home.R
import com.yapp.model.ScheduleProgressPhase
import com.yapp.core.designsystem.R as coreDesignR

@Composable
internal fun HomeStickHeader(
    modifier: Modifier = Modifier,
    initialPage: Int = 0,
    sessions: List<HomeState.Session>,
    upcomingSessionId: String,
    onClickSessionItem: (String) -> Unit
) {
    val pageIndex = sessions.indexOfFirst { it.id == upcomingSessionId }
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { sessions.size }
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.home_generation_title),
                style = YappTheme.typography.headline1Bold,
                color = YappTheme.colorScheme.staticWhite
            )
            Text(
                text = stringResource(R.string.home_session_all_title),
                style = YappTheme.typography.label1NormalBold,
                color = YappTheme.colorScheme.staticWhite
            )
        }
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
            contentPadding = PaddingValues(20.dp),
            pageSpacing = (-30).dp
        ) {
            val item = sessions[it]

            SessionItem(
                id = item.id,
                title = item.title,
                date = item.date,
                place = item.place,
                startTime = item.startTime,
                endTime = item.endTime,
                startDayOfWeek = item.startDayOfWeek,
                progressPhase = item.progressPhase,
                onClickSessionItem = onClickSessionItem
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(sessions.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) {
                    YappTheme.colorScheme.interactionDisable
                } else {
                    YappTheme.colorScheme.interactionInactive
                }
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(color = color, shape = CircleShape)
                        .size(8.dp)
                )
            }
        }
    }

    LaunchedEffect(pageIndex) {
        if (pageIndex > 0 && pagerState.currentPage != pageIndex) {
            pagerState.animateScrollToPage(pageIndex)
        }
    }
}

@Composable
private fun SessionItem(
    modifier: Modifier = Modifier,
    id: String,
    title: String,
    date: String,
    place: String,
    startTime: String,
    endTime: String,
    startDayOfWeek: String,
    progressPhase: ScheduleProgressPhase,
    onClickSessionItem: (String) -> Unit
) {
    val (clickableModifier, backgroundColor) = if (progressPhase != ScheduleProgressPhase.DONE) {
        Modifier.yappClickable { onClickSessionItem(id) } to YappTheme.colorScheme.backgroundNormalNormal
    } else {
        Modifier to YappTheme.colorScheme.backgroundNormalNormal.copy(alpha = 0.6f)
    }

    Column(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10)
            )
            .width(272.dp)
            .heightIn(min = 120.dp)
            .then(clickableModifier)
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SessionChip(progressPhase = progressPhase)
            Text(title, style = YappTheme.typography.body1NormalMedium)
        }
        Text("$date ($startDayOfWeek)", style = YappTheme.typography.caption1Bold)
        Spacer(modifier = Modifier.height(17.dp))
        Column {
            if (place.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(painter = painterResource(coreDesignR.drawable.icon_location), contentDescription = null)
                    Text(place, style = YappTheme.typography.caption1Bold.copy(color = YappTheme.colorScheme.labelAlternative))
                }
            }

            if (startTime.isNotEmpty() && endTime.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(painter = painterResource(coreDesignR.drawable.icon_time), contentDescription = null)
                    Text("$startTime - $endTime", style = YappTheme.typography.caption1Bold.copy(color = YappTheme.colorScheme.labelAlternative))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeStickHeaderPreview() {
    val colorStops = arrayOf(
        0.2f to YappTheme.colorScheme.primaryNormal,
        1f to YappTheme.colorScheme.secondaryNormal
    )

    HomeStickHeader(
        modifier = Modifier.background(brush = Brush.horizontalGradient(colorStops = colorStops)),
        sessions = listOf(
            HomeState.Session(
                id = "1",
                title = "2차 데모데이",
                date = "2025. 03. 15",
                place = "공덕 창업허브",
                startTime = "오후 6시",
                endTime = "오후 8시",
                startDayOfWeek = "금",
                progressPhase = ScheduleProgressPhase.TODAY
            )
        ),
        onClickSessionItem = {},
        upcomingSessionId = "123"
    )
}
