@file:Suppress("MagicNumber")

package com.yapp.testing.data

import com.yapp.model.AttendanceStatus
import com.yapp.model.DateGroupedSchedule
import com.yapp.model.HomeSession
import com.yapp.model.HomeSessionList
import com.yapp.model.NoticeInfo
import com.yapp.model.NoticeType
import com.yapp.model.ScheduleInfo
import com.yapp.model.ScheduleList
import com.yapp.model.ScheduleProgressPhase
import com.yapp.model.ScheduleType
import com.yapp.model.SessionDetailInfo
import com.yapp.model.SessionProgressPhase
import com.yapp.model.SessionType
import com.yapp.model.UpcomingSessionInfo
import com.yapp.model.UpcomingSessionNotice
import java.time.LocalDateTime

object ScheduleTestData {
    fun homeSessionList(): HomeSessionList = HomeSessionList(
        sessions = listOf(
            HomeSession(
                id = "session-1",
                name = "디자인 공유 세션",
                place = "판교 스테이션",
                date = "2024-05-18",
                dayOfWeek = "토",
                relativeDays = 2,
                startTime = "13:00",
                endTime = "16:00",
                progressPhase = SessionProgressPhase.PENDING,
                attendanceStatus = null
            ),
            HomeSession(
                id = "session-2",
                name = "모바일 개발 세션",
                place = "강남 스튜디오",
                date = "2024-05-11",
                dayOfWeek = "토",
                relativeDays = -5,
                startTime = "14:00",
                endTime = "18:00",
                progressPhase = SessionProgressPhase.DONE,
                attendanceStatus = AttendanceStatus.ATTENDED
            )
        ),
        upcomingSessionId = "session-upcoming",
        upcomingNotice = notices()
    )

    fun scheduleList(): ScheduleList = ScheduleList(
        dates = listOf(
            DateGroupedSchedule(
                date = "2024-05-18",
                isToday = false,
                dayOfTheWeek = "토",
                schedules = listOf(
                    ScheduleInfo(
                        id = "schedule-1",
                        name = "전체 세션",
                        place = "판교 스테이션",
                        date = "2024-05-18",
                        endDate = null,
                        startDayOfWeek = "토",
                        endDayOfWeek = null,
                        time = "13:00",
                        endTime = "16:00",
                        scheduleType = ScheduleType.SESSION,
                        sessionType = SessionType.OFFLINE,
                        scheduleProgressPhase = ScheduleProgressPhase.PENDING,
                        attendanceStatus = null,
                    )
                )
            ),
            DateGroupedSchedule(
                date = "2024-05-11",
                isToday = true,
                dayOfTheWeek = "토",
                schedules = listOf(
                    ScheduleInfo(
                        id = "schedule-2",
                        name = "운영 회의",
                        place = "온라인",
                        date = "2024-05-11",
                        endDate = null,
                        startDayOfWeek = "토",
                        endDayOfWeek = null,
                        time = "10:00",
                        endTime = "11:00",
                        scheduleType = ScheduleType.TASK,
                        sessionType = SessionType.ONLINE,
                        scheduleProgressPhase = ScheduleProgressPhase.ONGOING,
                        attendanceStatus = AttendanceStatus.ATTENDED,
                    )
                )
            )
        )
    )

    fun upcomingSessionInfo(): UpcomingSessionInfo = UpcomingSessionInfo(
        sessionId = "session-upcoming",
        name = "안드로이드 심화 세션",
        startDate = "2024-05-25",
        startDayOfTheWeek = "토",
        endDate = "2024-05-25",
        endDayOfTheWeek = "토",
        startTime = "13:00",
        endTime = "17:00",
        location = "홍대 스튜디오",
        remainingDays = 7,
        canCheckIn = false,
        status = null,
        progressPhase = SessionProgressPhase.PENDING,
        notices = listOf(
            UpcomingSessionNotice(
                id = "upcoming-notice-1",
                title = "입실은 12시 30분부터 가능합니다."
            ),
            UpcomingSessionNotice(
                id = "upcoming-notice-2",
                title = "간단한 간식이 제공됩니다."
            )
        )
    )

    fun sessionDetailInfo(): SessionDetailInfo = SessionDetailInfo(
        id = "session-1",
        progressPhase = SessionProgressPhase.PENDING,
        title = "전체 세션",
        startDateTime = LocalDateTime.of(2024, 5, 18, 13, 0),
        endDateTime = LocalDateTime.of(2024, 5, 18, 16, 30),
        place = "판교 스테이션",
        address = "경기도 성남시 판교로 242",
        latitude = 37.4018,
        longitude = 127.1087,
        notices = notices()
    )

    private fun notices(): List<NoticeInfo> = listOf(
        NoticeInfo(
            id = "notice-1",
            writerName = "운영국",
            writerId = "operation-1",
            writerPosition = "운영",
            writerGeneration = 15,
            createdAt = "2024-05-01T10:00:00+09:00",
            title = "세션 안내",
            content = "이번 주 세션은 판교 스테이션에서 진행됩니다.",
            noticeType = NoticeType.SESSION
        )
    )
}
