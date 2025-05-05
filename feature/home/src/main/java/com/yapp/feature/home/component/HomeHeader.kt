package com.yapp.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.SessionChip
import com.yapp.core.ui.util.formatTimeRange
import com.yapp.feature.home.HomeState
import com.yapp.feature.home.R
import com.yapp.model.ScheduleProgressPhase
import kotlinx.coroutines.launch
import com.yapp.core.designsystem.R as coreDesignR

@Composable
internal fun HomeHeader(
    modifier: Modifier = Modifier,
    sessions: List<HomeState.Session>,
    upcomingSessionId: String,
    onClickShowAll: () -> Unit,
) {
    val pageIndex = sessions.indexOfFirst { it.id == upcomingSessionId }
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(
        lazyListState = lazyListState,
        snapPosition = SnapPosition.Start
    )
    val selectedIndex by remember {
        derivedStateOf {
            val visibleItems = lazyListState.layoutInfo.visibleItemsInfo
            if (visibleItems.isEmpty()) 0
            else {
                val center = lazyListState.layoutInfo.viewportStartOffset +
                        lazyListState.layoutInfo.viewportEndOffset / 2
                visibleItems.minByOrNull {
                    val itemCenter = it.offset + it.size / 2
                    kotlin.math.abs(itemCenter - center)
                }?.index ?: 0
            }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    painter = painterResource(coreDesignR.drawable.home_notice),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.home_generation_title),
                    style = YappTheme.typography.headline1Bold,
                    color = YappTheme.colorScheme.staticWhite
                )
            }

            Text(
                modifier = Modifier.yappClickable(onClick = onClickShowAll),
                text = stringResource(R.string.home_session_all_title),
                style = YappTheme.typography.label1NormalBold,
                color = YappTheme.colorScheme.staticWhite
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            state = lazyListState,
            flingBehavior = flingBehavior,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(
                sessions,
                key = { it.id }
            ) {
                SessionItem(
                    id = it.id,
                    title = it.title,
                    date = it.date,
                    place = it.place,
                    startTime = it.startTime,
                    endTime = it.endTime,
                    startDayOfWeek = it.startDayOfWeek,
                    progressPhase = it.progressPhase,
                    onClickSessionItem = {  }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Indicators(
            itemCount = sessions.size,
            onPageSelect = { index ->
                scope.launch {
                    lazyListState.scrollToItem(index)
                }
            },
            currentPage = selectedIndex
        )

        Spacer(modifier = Modifier.height(16.dp))
    }

    LaunchedEffect(pageIndex) {
        if (pageIndex > 0 && selectedIndex != pageIndex) {
            lazyListState.scrollToItem(pageIndex)
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
    val context = LocalContext.current

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

            val timeRange = formatTimeRange(
                context = context,
                startTime = startTime,
                endTime = endTime
            )

            if (timeRange.orEmpty().isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(painter = painterResource(coreDesignR.drawable.icon_time), contentDescription = null)
                    Text(timeRange.orEmpty(), style = YappTheme.typography.caption1Bold.copy(color = YappTheme.colorScheme.labelAlternative))
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

    HomeHeader(
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
        onClickShowAll = { },
        upcomingSessionId = "123"
    )
}
