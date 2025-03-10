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
    val noticeType: NoticeType
) {
    val writerLabel = "${writerGeneration}기 $writerName"
}

enum class NoticeType(val apiValue: String, val displayValue: String) {
    ALL("ALL", "전체"),
    OPERATION("OPERATION", "운영"),
    SESSION("SESSION", "세션");

    companion object {
        fun fromApiValue(apiValue: String): NoticeType {
            return (entries.find { it.apiValue == apiValue }) ?: ALL
        }
    }
}
