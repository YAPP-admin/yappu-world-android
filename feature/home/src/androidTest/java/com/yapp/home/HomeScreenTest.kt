package com.yapp.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.feature.home.HomeScreen
import com.yapp.feature.home.HomeState
import com.yapp.feature.home.R
import com.yapp.model.AttendanceStatus
import com.yapp.model.SessionProgressPhase
import com.yapp.model.UpcomingSessionInfo
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun setHomeScreenContent(state: HomeState) {
        composeTestRule.setContent {
            YappTheme {
                HomeScreen(
                    homeState = state,
                    onIntent = { }
                )
            }
        }
    }

    private fun defaultUpcomingSessionInfo(
        sessionId: String = "",
        name: String = "",
        startDate: String = "",
        startDayOfTheWeek: String = "",
        endDate: String = "",
        endDayOfTheWeek: String = "",
        startTime: String = "",
        endTime: String = "",
        location: String = "",
        remainingDays: Int = 0,
        canCheckIn: Boolean = false,
        status: AttendanceStatus? = null,
    ): UpcomingSessionInfo = UpcomingSessionInfo(
        sessionId = sessionId,
        name = name,
        startDate = startDate,
        startDayOfTheWeek = startDayOfTheWeek,
        endDate = endDate,
        endDayOfTheWeek = endDayOfTheWeek,
        startTime = startTime,
        endTime = endTime,
        location = location,
        remainingDays = remainingDays,
        canCheckIn = canCheckIn,
        status = status,
        progressPhase = SessionProgressPhase.NONE,
        notices = emptyList()
    )

    @Test
    fun 오늘의_세션에_출석할_수_있는_시간이라면_버튼을_활성화한다() {
        // given
        val state = HomeState(
            isLoading = false,
            upcomingSession = defaultUpcomingSessionInfo(
                canCheckIn = true
            )
        )

        // when
        setHomeScreenContent(state)

        // then
        composeTestRule
            .onNodeWithTag("attendanceButton")
            .assertIsEnabled()
    }

    @Test
    fun 오늘의_세션에_제시간에_출석_완료_시_출석완료가_표시된다() {
        // given
        val state = HomeState(
            isLoading = false,
            upcomingSession = defaultUpcomingSessionInfo(
                status = AttendanceStatus.ATTENDED
            )
        )

        // when
        setHomeScreenContent(state)

        // then
        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(R.string.session_attendance_done)
            )
            .assertExists()
    }

    @Test
    fun 오늘의_세션에_지각할_시_지각이_표시된다() {
        // given
        val state = HomeState(
            isLoading = false,
            upcomingSession = defaultUpcomingSessionInfo(
                status = AttendanceStatus.LATE
            )
        )

        // when
        setHomeScreenContent(state)

        // then
        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(R.string.session_attendance_late)
            )
            .assertExists()
    }

    @Test
    fun 오늘의_세션에_결석할_시_결석이_표시된다() {
        // given
        val state = HomeState(
            isLoading = false,
            upcomingSession = defaultUpcomingSessionInfo(
                status = AttendanceStatus.ABSENT
            )
        )

        // when
        setHomeScreenContent(state)

        // then
        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(R.string.session_attendance_absent)
            )
            .assertExists()
    }

    @Test
    fun 오늘의_세션에_조퇴할_시_조퇴가_표시된다() {
        // given
        val state = HomeState(
            isLoading = false,
            upcomingSession = defaultUpcomingSessionInfo(
                status = AttendanceStatus.EARLY_LEAVE
            )
        )

        // when
        setHomeScreenContent(state)

        // then
        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(R.string.session_attendance_early_leave)
            )
            .assertExists()
    }

    @Test
    fun 오늘의_세션에_공결일_시_공결이_표시된다() {
        // given
        val state = HomeState(
            isLoading = false,
            upcomingSession = defaultUpcomingSessionInfo(
                status = AttendanceStatus.EXCUSED
            )
        )

        // when
        setHomeScreenContent(state)

        // then
        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(R.string.session_attendance_excused)
            )
            .assertExists()
    }

    @Test
    fun 다음_세션이_존재하지_않는다면_출석_버튼을_노출하지_않는다() {
        // given
        val state = HomeState(
            isLoading = false,
            upcomingSession = null,
        )

        // when
        setHomeScreenContent(state)

        // then
        composeTestRule
            .onNodeWithTag("upcomingSessionCard")
            .assertDoesNotExist()
    }

    @Test
    fun 다음_세션이_존재하지_않는다면_상세보기_버튼을_노출하지_않는다() {
        // given
        val state = HomeState(
            isLoading = false,
            upcomingSession = null,
        )

        // when
        setHomeScreenContent(state)

        // then
        composeTestRule
            .onNodeWithTag("upcomingSessionDetailButton")
            .assertDoesNotExist()
    }
}