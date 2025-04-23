package com.yapp.feature.notice.noticedetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappSkeleton


@Composable
fun NoticeDetailLoading(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)

    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            YappSkeleton(
                modifier = Modifier
                    .width(190.dp)
                    .height(15.75.dp),
                radius = (22.5)
            )
            YappSkeleton(
                modifier = Modifier
                    .width(197.dp)
                    .height(15.75.dp),
                radius = (22.5)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                YappSkeleton(
                    modifier = Modifier
                        .width(32.dp)
                        .height(20.dp)
                )
                Text(
                    text = "∙",
                    color = YappTheme.colorScheme.labelAssistive,
                    style = YappTheme.typography.caption1Regular
                )
                YappSkeleton(
                    modifier = Modifier
                        .width(32.dp)
                        .height(10.5.dp)
                )
                YappSkeleton(
                    modifier = Modifier
                        .width(45.dp)
                        .height(10.5.dp)
                )

            }
        }
        Spacer(Modifier.height(24.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val widthList = listOf(317, 295, 220, 294, 235, 253, 169, 238, 190, 189, 289, 229, 232)
            widthList.forEach { width ->
                YappSkeleton(
                    modifier = Modifier
                        .width(width.dp)
                        .height(13.12.dp),
                    radius = (18.75)
                )
            }
        }
    }
}
