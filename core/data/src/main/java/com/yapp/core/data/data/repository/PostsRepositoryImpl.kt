package com.yapp.core.data.data.repository

import com.yapp.core.data.remote.api.PostsApi
import com.yapp.dataapi.PostsRepository
import com.yapp.model.NoticeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class PostsRepositoryImpl @Inject constructor(
    private val postsApi: PostsApi,
) : PostsRepository {
    override suspend fun getNoticeList(
        lastNoticeId: String?,
        limit: Int,
        noticeType: String,
    ) = flow {
        val response = postsApi.getNoticeList(
            lastCursorId = lastNoticeId,
            limit = limit,
            noticeType = noticeType
        )
        emit(response.toNoticeListModel())
    }

    override suspend fun getNoticeItem(noticeId: String): Flow<NoticeInfo> = flow {
        val response = postsApi.getNoticeItem(noticeId)
        emit(response.toNoticeModel())
    }
}