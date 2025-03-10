package com.yapp.core.data.data.repository

import com.yapp.core.data.remote.api.PostsApi
import com.yapp.core.data.remote.model.response.NoticeType
import com.yapp.dataapi.PostsRepository
import com.yapp.model.NoticeList
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class PostsRepositoryImpl @Inject constructor(
    private val postsApi: PostsApi,
) : PostsRepository {
    override suspend fun getNoticeList(
        lastNoticeId: String?,
        limit: Int,
        noticeType: String,
    ) = flow<NoticeList> {
        val response = postsApi.getNoticeList(
            lastCursorId = lastNoticeId,
            limit = limit,
            noticeType = NoticeType.fromDisplayName(noticeType)
        )
        emit(response.toNoticeListModel())
    }
}