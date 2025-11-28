package com.yapp.home

import com.yapp.feature.home.HomeIntent
import com.yapp.feature.home.HomeSideEffect
import com.yapp.feature.home.HomeViewModel
import com.yapp.model.AttendanceInfo
import com.yapp.model.AttendanceStatus
import com.yapp.model.exceptions.CodeNotCorrectException
import com.yapp.testing.MainDispatcherRule
import com.yapp.testing.repository.FakeAttendanceRepository
import com.yapp.testing.repository.FakeOperationsRepository
import com.yapp.testing.repository.FakeScheduleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var fakeScheduleRepository: FakeScheduleRepository
    private lateinit var fakeAttendanceRepository: FakeAttendanceRepository
    private lateinit var fakeOperationsRepository: FakeOperationsRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        fakeScheduleRepository = FakeScheduleRepository()
        fakeAttendanceRepository = FakeAttendanceRepository()
        fakeOperationsRepository = FakeOperationsRepository()

        viewModel = HomeViewModel(
            scheduleRepository = fakeScheduleRepository,
            attendanceRepository = fakeAttendanceRepository,
            operationsRepository = fakeOperationsRepository
        )
    }

    @Test
    fun 홈_화면의_초기_상태가_로딩_중이어야_한다() = runTest {
        // when
        val state = viewModel.store.uiState.value

        // then
        assertTrue(state.isLoading)
    }

    @Test
    fun 홈_화면에_입장하면_다가오는_세션을_가져오고_로딩을_종료한다() = runTest {
        // when
        viewModel.store.onIntent(HomeIntent.EnterHomeScreen)

        // then
        assertEquals(1, fakeScheduleRepository.refreshUpcomingSessionsCount)
        val state = viewModel.store.uiState.value
        assertFalse(state.isLoading)
        assertEquals(fakeScheduleRepository.upcomingSessionInfo, state.upcomingSession)
    }

    @Test
    fun 홈_화면에_재진입해도_처음_한번만_다가오는_세션을_조회한다() = runTest {
        // when
        viewModel.store.onIntent(HomeIntent.EnterHomeScreen)
        viewModel.store.onIntent(HomeIntent.EnterHomeScreen)

        // then
        assertEquals(1, fakeScheduleRepository.refreshUpcomingSessionsCount)
    }

    @Test
    fun 새로고침_시_항상_다가오는_세션을_재조회한다() = runTest {
        // when
        viewModel.store.onIntent(HomeIntent.Refresh)
        viewModel.store.onIntent(HomeIntent.Refresh)

        // then
        assertEquals(2, fakeScheduleRepository.refreshUpcomingSessionsCount)
    }

    @Test
    fun 기본_규칙_링크를_처음_요청하면_API를_호출하고_URL을_연다() = runTest {
        // given
        val effectDeferred = async { viewModel.store.sideEffects.first() }

        // when
        viewModel.store.onIntent(HomeIntent.ClickBasicRuleLink)

        // then
        assertEquals(1, fakeOperationsRepository.basicRuleRequestCount)
        assertEquals(HomeSideEffect.OpenUrl(fakeOperationsRepository.basicRuleLink), effectDeferred.await())
    }

    @Test
    fun 기본_규칙_링크는_캐시된_URL을_사용해_추가_API_호출을_하지_않는다() = runTest {
        // given
        viewModel.store.onIntent(HomeIntent.ClickBasicRuleLink)
        assertEquals(1, fakeOperationsRepository.basicRuleRequestCount)

        // when
        val effectDeferred = async { viewModel.store.sideEffects.first() }
        viewModel.store.onIntent(HomeIntent.ClickBasicRuleLink)

        // then
        assertEquals(1, fakeOperationsRepository.basicRuleRequestCount)
        assertEquals(HomeSideEffect.OpenUrl(fakeOperationsRepository.basicRuleLink), effectDeferred.await())
    }

    @Test
    fun 출석코드_입력_다이얼로그를_열고_닫으면_입력_상태가_초기화_되어있다() = runTest {
        // when
        viewModel.store.onIntent(HomeIntent.ClickRequestAttendCode)
        assertTrue(viewModel.store.uiState.value.showAttendCodeBottomSheet)
        viewModel.store.onIntent(HomeIntent.ClickDismissDialog)
        val state = viewModel.store.uiState.value

        // then
        assertFalse(state.showAttendCodeBottomSheet)
        assertEquals(List(4) { "" }, state.attendanceCodeDigits)
        assertFalse(state.showAttendanceCodeError)
    }

    @Test
    fun 출석코드_입력값을_변경하면_버튼_활성화_여부가_동기화된다() = runTest {
        // when
        val digits = listOf("1", "2", "3", "4")
        viewModel.store.onIntent(HomeIntent.ChangeAttendanceCodeDigits(digits))
        assertTrue(viewModel.store.uiState.value.inputCompleteButtonEnabled)

        // and when
        viewModel.store.onIntent(HomeIntent.ChangeAttendanceCodeDigits(listOf("1", "2", "", "4")))

        // then
        assertFalse(viewModel.store.uiState.value.inputCompleteButtonEnabled)
    }

    @Test
    fun 출석_요청에_성공하면_출석정보를_갱신하고_다이얼로그를_닫는다() = runTest {
        // given
        fakeScheduleRepository.upcomingSessionInfo = fakeScheduleRepository.upcomingSessionInfo.copy(canCheckIn = true)
        viewModel.store.onIntent(HomeIntent.EnterHomeScreen)

        // when
        viewModel.store.onIntent(HomeIntent.ClickRequestAttendCode)
        val codeDigits = listOf("1", "2", "3", "4")
        viewModel.store.onIntent(HomeIntent.ChangeAttendanceCodeDigits(codeDigits))

        viewModel.store.onIntent(HomeIntent.ClickRequestAttendance)

        // then
        val state = viewModel.store.uiState.value
        assertFalse(state.showAttendCodeBottomSheet)
        assertFalse(state.showAttendanceCodeError)
        assertEquals(AttendanceStatus.ATTENDED, state.upcomingSession?.status)
        assertFalse(state.upcomingSession?.canCheckIn ?: true)
        assertEquals(
            listOf(AttendanceInfo("session-upcoming", "1234")),
            fakeAttendanceRepository.postedAttendances
        )
    }

    @Test
    fun 출석코드가_틀리면_에러를_표시한다() = runTest {
        // given
        fakeScheduleRepository.upcomingSessionInfo = fakeScheduleRepository.upcomingSessionInfo.copy(canCheckIn = true)
        viewModel.store.onIntent(HomeIntent.EnterHomeScreen)

        viewModel.store.onIntent(HomeIntent.ClickRequestAttendCode)
        viewModel.store.onIntent(HomeIntent.ChangeAttendanceCodeDigits(listOf("1", "2", "3", "4")))
        fakeAttendanceRepository.postAttendanceResult = Result.failure(CodeNotCorrectException())

        // when
        viewModel.store.onIntent(HomeIntent.ClickRequestAttendance)

        // then
        assertTrue(viewModel.store.uiState.value.showAttendanceCodeError)
    }
}
