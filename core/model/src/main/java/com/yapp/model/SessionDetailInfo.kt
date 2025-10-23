package com.yapp.model

import java.time.LocalDateTime

data class SessionDetailInfo(
    val id: String,
    val progressPhase: SessionProgressPhase,
    val title: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val place: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val notices: List<NoticeInfo>
)