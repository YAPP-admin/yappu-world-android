package com.yapp.core.data.remote.model.response

import com.yapp.model.NoticeInfo
import com.yapp.model.NoticeList
import com.yapp.model.NoticeType
import kotlinx.serialization.Serializable

@Serializable
data class NoticeListResponse(
    val data: List<NoticeData>,
    val lastCursor: String,
    val limit: Int,
    val hasNext: Boolean
){
    fun toNoticeListModel() = NoticeList(
        notices = data.map { it.toNoticeModel() },
        lastNoticeId = lastCursor,
        hasNext = hasNext
    )
}

@Serializable
data class NoticeData(
    val notice: Notice,
    val writer: Writer
){
    fun toNoticeModel() = NoticeInfo(
        id = notice.id,
        writerName = writer.name,
        writerId = writer.id,
        writerPosition = writer.activityUnitPosition.label,
        writerGeneration = writer.activityUnitGeneration,
        createdAt = notice.createdAt,
        title = notice.title,
        content = notice.content,
        noticeType = NoticeType.fromApiValue(notice.noticeType)
    )
}

@Serializable
data class Notice(
    val id: String,
    val createdAt: String,
    val title: String,
    val content: String,
    val noticeType: String
)

@Serializable
data class Writer(
    val id: String,
    val name : String,
    val activityUnitGeneration: Int,
    val activityUnitPosition: ActivityUnitPosition
)

@Serializable
data class ActivityUnitPosition(
    val name: String,
    val label: String
)
