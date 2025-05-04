package com.yapp.dataapi

import com.yapp.model.NoticeInfo
import com.yapp.model.NoticeList
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    fun getNoticeList(
        lastNoticeId: String?,
        limit: Int,
        noticeType: String
    ): Flow<NoticeList>

    fun getNoticeItem(
        noticeId: String
    ): Flow<NoticeInfo>
}