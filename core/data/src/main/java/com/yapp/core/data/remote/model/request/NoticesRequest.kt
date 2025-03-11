package com.yapp.core.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
internal data class NoticeListRequest(
    val lastCursorId : String?,
    val limit : Int,
    val noticeType : String
)
