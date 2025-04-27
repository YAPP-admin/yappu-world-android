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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.feature.home.HomeState
import com.yapp.feature.home.R
import com.yapp.core.designsystem.R as coreDesignR

@Composable
internal fun HomeStickHeader(
    modifier: Modifier = Modifier,
    initialPage: Int = 0,
    sessions: List<HomeState.Session>
) {
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
            contentPadding = PaddingValues(24.dp),
            pageSpacing = 8.dp
        ) {
            val item = sessions[it]

            SessionItem(
                title = item.title,
                date = item.date,
                place = item.place,
                startTime = item.startTime,
                endTime = item.endTime
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
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
}

@Composable
private fun SessionItem(
    modifier: Modifier = Modifier,
    title: String,
    date: String,
    place: String,
    startTime: String,
    endTime: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 120.dp)
            .background(
                color = YappTheme.colorScheme.staticWhite,
                shape = RoundedCornerShape(10)
            ).padding(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //todo 태그
            Text(title, style = YappTheme.typography.body1NormalMedium)
        }
        Text(date, style = YappTheme.typography.label1NormalMedium)
        Spacer(modifier = Modifier.height(17.dp))
        Column {
            if (place.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(painter = painterResource(coreDesignR.drawable.icon_location), contentDescription = null)
                    Text(place, style = YappTheme.typography.label2Medium)
                }
            }

            if (startTime.isNotEmpty() && endTime.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(painter = painterResource(coreDesignR.drawable.icon_time), contentDescription = null)
                    Text("$startTime - $endTime", style = YappTheme.typography.label2Medium)
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
                title = "2차 데모데이",
                date = "2025. 03. 15 (토)",
                place = "공덕 창업허브",
                startTime = "오후 6시",
                endTime = "오후 8시"
            )
        )
    )
}
