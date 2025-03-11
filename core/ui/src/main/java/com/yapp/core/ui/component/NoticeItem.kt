package com.yapp.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.chip.ChipColorType
import com.yapp.core.designsystem.component.chip.YappChipSmall
import com.yapp.core.designsystem.extension.yappClickable
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.model.NoticeInfo
import dev.jeziellago.compose.markdowntext.MarkdownText


@Composable
fun NoticeItem(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    noticeInfo: NoticeInfo,
) {
    val columModifier = modifier
        .padding(vertical = 9.dp)
        .then(onClick?.let { Modifier.yappClickable(onClick = it) } ?: Modifier)

    Column(
        modifier = columModifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            YappChipSmall(
                text = noticeInfo.noticeType.displayValue,
                colorType = ChipColorType.Gray,
                isFill = false
            )
            Text(
                text = noticeInfo.writerLabel,
                color = YappTheme.colorScheme.labelAssistive,
                style = YappTheme.typography.caption1Regular
            )
            Text(
                text = "∙",
                color = YappTheme.colorScheme.labelAssistive,
                style = YappTheme.typography.caption1Regular
            )
            Text(
                text = noticeInfo.createdAt,
                color = YappTheme.colorScheme.labelAssistive,
                style = YappTheme.typography.caption1Regular
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = noticeInfo.title,
            color = YappTheme.colorScheme.labelNormal,
            style = YappTheme.typography.body2NormalBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(8.dp))
        MarkdownText(
            style = YappTheme.typography.label1ReadingRegular,
            markdown = noticeInfo.content,
            maxLines = 2,
        )
    }
}

@Composable
fun NoticeLoadingItem(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(vertical = 9.dp)
    ) {
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
        Spacer(Modifier.height(8.dp))
        YappSkeleton(
            modifier = Modifier
                .fillMaxWidth()
                .height(13.dp), radius = (18.75)
        )
        Spacer(Modifier.height(8.dp))
        YappSkeleton(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .padding(end = 5.dp),
            radius = (17.5)
        )
        Spacer(Modifier.height(8.dp))
        YappSkeleton(
            modifier = Modifier
                .width(200.dp)
                .height(12.25.dp), radius = (17.5)
        )
        Spacer(Modifier.height(8.dp))
        YappSkeleton(
            modifier = Modifier
                .width(264.dp)
                .height(12.25.dp), radius = (17.5)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun NoticeItemPreview() {
    YappTheme {
        LazyColumn {
        }
    }
}

