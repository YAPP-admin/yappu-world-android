package com.yapp.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.chip.ChipColorType
import com.yapp.core.designsystem.component.chip.YappChipSmall
import com.yapp.core.designsystem.theme.YappTheme

// 샘플 데이터 .. .. ..
data class NoticeInfo(
    val tags : List<TagInfo>,
    val writer : String,
    val creationDate : String,
    val title : String,
    val bodyText : String
)

data class TagInfo(
    val tagText : String,
    val tagColor : ChipColorType
)

@Composable
fun NoticeItem(
    noticeInfo: NoticeInfo
){
    Column(
        modifier = Modifier.padding(vertical = 9.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp), 
            verticalAlignment = Alignment.CenterVertically
        ) {
            noticeInfo.tags.forEach { tag ->
                YappChipSmall(
                    text = tag.tagText,
                    colorType = tag.tagColor,
                    isFill = false
                )
            }
            Text(
                text = noticeInfo.writer,
                color = YappTheme.colorScheme.labelAssistive,
                style = YappTheme.typography.caption1Regular
            )
            Text(
                text = "∙",
                color = YappTheme.colorScheme.labelAssistive,
                style = YappTheme.typography.caption1Regular
            )
            Text(
                text = noticeInfo.creationDate,
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
        Text(
            text = noticeInfo.bodyText,
            color = YappTheme.colorScheme.labelNormal,
            style = YappTheme.typography.label1ReadingRegular,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
    HorizontalDivider(
        color = YappTheme.colorScheme.lineNormalAlternative
    )
}


@Composable
@Preview(showBackground = true)
fun NoticeItemPreview(){
    val noticeInfo = listOf(
        NoticeInfo(
            tags = listOf(TagInfo("운영", ChipColorType.Gray),TagInfo("정회원", ChipColorType.Sub)),
            creationDate = "2023-08-13",
            writer = "20기 홍길동",
            title = "심장 건강을 책임지는 스마트 워치,심박수 감시와 예방 기능 탑재",
            bodyText = "한반도의 경제 협력이 새로운 국면을 맞이하며 남북 간 첫 연합 기업이 설립되었습니다. 이 기업은 에너지, 통신, 제조 등 다양한 분야에서 남북 간 협력 모델을 제시하며 경제적 도약을 목표로 하고 있습니다. 특히, 이번 연합 기업은 지속 가능한 발전을 위한 친환경 기술과 공동 연구 개발을 핵심 과제로 삼고 있으며, 이를 통해 글로벌 시장에서도 경쟁력을 확보하고자 합니다. 전문가들은 이러한 경제 협력이 한반도뿐만 아니라 동아시아 전체의 경제적 안정과 성장에도 기여할 것으로 예상하고 있습니다."
        ),
        NoticeInfo(
            tags = listOf(TagInfo("세션", ChipColorType.Main),TagInfo("정회원", ChipColorType.Sub)),
            creationDate = "2023-08-13",
            writer = "20기 홍길동",
            title = "심장 건강을 책임지는 스마트 워치,심박수 감시와 예방 기능 탑재심장 건강을 책임지는 스마트 워치,심박수 감시와 예방 기능 탑재",
            bodyText = "한반도의 경제 협력이 새로운 국면을 맞이하며 남북 간 첫 연합 기업이 설립되었습니다. 이 기업은 에너지, 통신, 제조 등 다양한 분야에서 남북 간 협력 모델을 제시하며 경제적 도약을 목표로 하고 있습니다. 특히, 이번 연합 기업은 지속 가능한 발전을 위한 친환경 기술과 공동 연구 개발을 핵심 과제로 삼고 있으며, 이를 통해 글로벌 시장에서도 경쟁력을 확보하고자 합니다. 전문가들은 이러한 경제 협력이 한반도뿐만 아니라 동아시아 전체의 경제적 안정과 성장에도 기여할 것으로 예상하고 있습니다."
        ),
        NoticeInfo(
            tags = listOf(TagInfo("세션", ChipColorType.Main),TagInfo("정회원", ChipColorType.Sub)),
            creationDate = "2023-08-13",
            writer = "20기 홍길동",
            title = "심장 건강을 책임지는 스마트 워치,심박수 감시와 예방 기능 탑재",
            bodyText = "한반도의 경제 협력이 새로운 국면을 맞이하며 남북 간 첫 연합 기업이 설립되었습니다. 이 기업은 에너지, 통신, 제조 등 다양한 분야에서 남북 간 협력 모델을 제시하며 경제적 도약을 목표로 하고 있습니다. 특히, 이번 연합 기업은 지속 가능한 발전을 위한 친환경 기술과 공동 연구 개발을 핵심 과제로 삼고 있으며, 이를 통해 글로벌 시장에서도 경쟁력을 확보하고자 합니다. 전문가들은 이러한 경제 협력이 한반도뿐만 아니라 동아시아 전체의 경제적 안정과 성장에도 기여할 것으로 예상하고 있습니다."
        )
    )
    YappTheme{
        LazyColumn {
            items(noticeInfo) { item ->
                NoticeItem(item)
            }
        }
    }
}

