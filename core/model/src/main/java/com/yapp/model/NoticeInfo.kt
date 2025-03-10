package com.yapp.model


data class NoticeList(
    val notices: List<NoticeInfo>,
    val lastNoticeId: String,
    val hasNext: Boolean,
)

data class NoticeInfo(
    val id: String,
    val writerName: String,
    val writerId: String,
    val writerPosition: String,
    val writerGeneration: Int,
    val createdAt: String,
    val title: String,
    val content: String,
    val noticeType: String
) {
    val writerLabel = "${writerGeneration}기 $writerName"
}

