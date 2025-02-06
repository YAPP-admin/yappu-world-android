package com.yapp.feature.home

import com.yapp.core.designsystem.component.chip.ChipColorType
import com.yapp.core.ui.component.NoticeInfo
import com.yapp.core.ui.component.TagInfo
import com.yapp.model.ActivityUnit

data class HomeState(
    val name : String = "김재원",
    val activityUnits : List<ActivityUnit> = listOf(ActivityUnit(position = "Android", generation = 25)),
    var noticeInfo: List<NoticeInfo> = listOf()
){
    init {
        // TODO :: 프로필 API 연동때 수정
        noticeInfo = listOf(
            NoticeInfo(
                tags = listOf(TagInfo("운영", ChipColorType.Gray), TagInfo("정회원", ChipColorType.Sub)),
                creationDate = "2023-08-13",
                writer = "20기 홍길동",
                title = "심장 건강을 책임지는 스마트 워치,심박수 감시와 예방 기능 탑재",
                bodyText = "한반도의 경제 협력이 새로운 국면을 맞이하며 남북 간 첫 연합 기업이 설립되었습니다. 이 기업은 에너지, 통신, 제조 등 다양한 분야에서 남북 간 협력 모델을 제시하며 경제적 도약을 목표로 하고 있습니다. 특히, 이번 연합 기업은 지속 가능한 발전을 위한 친환경 기술과 공동 연구 개발을 핵심 과제로 삼고 있으며, 이를 통해 글로벌 시장에서도 경쟁력을 확보하고자 합니다. 전문가들은 이러한 경제 협력이 한반도뿐만 아니라 동아시아 전체의 경제적 안정과 성장에도 기여할 것으로 예상하고 있습니다."
            ),
            NoticeInfo(
                tags = listOf(TagInfo("세션", ChipColorType.Main), TagInfo("정회원", ChipColorType.Sub)),
                creationDate = "2023-08-13",
                writer = "20기 홍길동",
                title = "심장 건강을 책임지는 스마트 워치,심박수 감시와 예방 기능 탑재심장 건강을 책임지는 스마트 워치,심박수 감시와 예방 기능 탑재",
                bodyText = "한반도의 경제 협력이 새로운 국면을 맞이하며 남북 간 첫 연합 기업이 설립되었습니다. 이 기업은 에너지, 통신, 제조 등 다양한 분야에서 남북 간 협력 모델을 제시하며 경제적 도약을 목표로 하고 있습니다. 특히, 이번 연합 기업은 지속 가능한 발전을 위한 친환경 기술과 공동 연구 개발을 핵심 과제로 삼고 있으며, 이를 통해 글로벌 시장에서도 경쟁력을 확보하고자 합니다. 전문가들은 이러한 경제 협력이 한반도뿐만 아니라 동아시아 전체의 경제적 안정과 성장에도 기여할 것으로 예상하고 있습니다."
            ),
            NoticeInfo(
                tags = listOf(TagInfo("세션", ChipColorType.Main), TagInfo("정회원", ChipColorType.Sub)),
                creationDate = "2023-08-13",
                writer = "20기 홍길동",
                title = "심장 건강을 책임지는 스마트 워치,심박수 감시와 예방 기능 탑재",
                bodyText = "한반도의 경제 협력이 새로운 국면을 맞이하며 남북 간 첫 연합 기업이 설립되었습니다. 이 기업은 에너지, 통신, 제조 등 다양한 분야에서 남북 간 협력 모델을 제시하며 경제적 도약을 목표로 하고 있습니다. 특히, 이번 연합 기업은 지속 가능한 발전을 위한 친환경 기술과 공동 연구 개발을 핵심 과제로 삼고 있으며, 이를 통해 글로벌 시장에서도 경쟁력을 확보하고자 합니다. 전문가들은 이러한 경제 협력이 한반도뿐만 아니라 동아시아 전체의 경제적 안정과 성장에도 기여할 것으로 예상하고 있습니다."
            )
        )
    }
}

sealed interface HomeIntent {
    data object ClickMoreButton : HomeIntent
    data object ClickSettingButton : HomeIntent
}

sealed interface HomeSideEffect {
    data object NavigateToNotice : HomeSideEffect
    data object NavigateToSetting : HomeSideEffect
}
