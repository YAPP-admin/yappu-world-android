package com.yapp.model

data class SessionDetailInfo(
    val id: String,
    val progressPhase: SessionProgressPhase,
    val title: String,
    val startDate: String,
    val startTime: String?,
    val startDayOfWeek: String,
    val endDate: String?,
    val endTime: String?,
    val endDayOfWeek: String?,
    val place: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val notices: List<NoticeInfo>
) {
    val dateTimeText: String
        get() {
            val formattedStartDate = startDate.replace("-", ". ")
            val formattedEndDate = endDate?.replace("-", ". ")

            val timeRange = if (startTime != null && endTime != null) {
                " / $startTime - $endTime"
            } else if (startTime != null) {
                " / $startTime"
            } else {
                ""
            }

            return if (formattedEndDate != null && endDayOfWeek != null) {
                "$formattedStartDate ($startDayOfWeek)$timeRange ~ $formattedEndDate ($endDayOfWeek)$timeRange"
            } else {
                "$formattedStartDate ($startDayOfWeek)$timeRange"
            }
        }
}