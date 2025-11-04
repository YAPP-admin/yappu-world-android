package com.yapp.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.SessionChip
import com.yapp.core.ui.util.formatTimeRange
import com.yapp.model.SessionProgressPhase
import com.yapp.core.designsystem.R as coreDesignR

@Composable
internal fun HomeHeader(
    modifier: Modifier = Modifier,
    body: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = YappTheme.colorScheme.staticWhite)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        body()
    }
}

@Composable
private fun SessionItem(
    modifier: Modifier = Modifier,
    id: String,
    title: String,
    date: String,
    place: String,
    startTime: String?,
    endTime: String?,
    dayOfWeek: String,
    progressPhase: SessionProgressPhase,
    showSessionChip: Boolean,
    onClickSessionItem: (String) -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val (clickableModifier, backgroundColor) = if (progressPhase != SessionProgressPhase.DONE) {
        Modifier.yappClickable { onClickSessionItem(id) } to YappTheme.colorScheme.backgroundNormalNormal
    } else {
        Modifier to YappTheme.colorScheme.backgroundNormalNormal.copy(alpha = 0.6f)
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10))
            .background(color = backgroundColor,)
            .width(configuration.screenWidthDp.dp - 88.dp)
            .heightIn(min = 120.dp)
            .then(clickableModifier)
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showSessionChip) SessionChip(progressPhase = progressPhase)
            Text(title, style = YappTheme.typography.body1NormalMedium)
        }
        Text("$date ($dayOfWeek)", style = YappTheme.typography.caption1Bold)
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

    YappTheme {
        HomeHeader(
            modifier = Modifier.background(brush = Brush.horizontalGradient(colorStops = colorStops)),
            body = {},
        )
    }
}
