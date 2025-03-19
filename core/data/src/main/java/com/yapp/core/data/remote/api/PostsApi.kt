package com.yapp.core.data.remote.api

import com.yapp.core.data.remote.model.response.NoticeData
import com.yapp.core.data.remote.model.response.NoticeListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface PostsApi {
    @GET("v1/posts/notices")
    suspend fun getNoticeList(
        @Query("lastCursorId") lastCursorId: String?,
        @Query("limit") limit: Int,
        @Query("noticeType") noticeType: String,
    ) : NoticeListResponse

    @GET("v1/posts/notices/{noticeId}")
    suspend fun getNoticeItem(@Path("noticeId") noticeId: String): NoticeData
}