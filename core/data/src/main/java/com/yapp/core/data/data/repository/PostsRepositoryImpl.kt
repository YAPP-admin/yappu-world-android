package com.yapp.core.data.data.repository

import com.yapp.core.data.remote.api.PostsApi
import com.yapp.dataapi.PostsRepository
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
}