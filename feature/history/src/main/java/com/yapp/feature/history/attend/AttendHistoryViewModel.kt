package com.yapp.feature.history.attend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.AttendanceRepository
import com.yapp.domain.runCatchingIgnoreCancelled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AttendHistoryViewModel @Inject constructor(
    private val attendanceRepository: AttendanceRepository,
): ViewModel() {

    val store: MviIntentStore<AttendHistoryState, AttendHistoryIntent, AttendHistorySideEffect> = mviIntentStore(
        initialState = AttendHistoryState(),
        onIntent = ::onIntent,
    )

    private fun onIntent(
        intent: AttendHistoryIntent,
        state: AttendHistoryState,
        reduce: (AttendHistoryState.() -> AttendHistoryState) -> Unit,
        sideEffect: (AttendHistorySideEffect) -> Unit
    ) {
        when(intent) {
            AttendHistoryIntent.OnEntryScreen -> {
                loadAttendanceStatistics(reduce = reduce)
                loadAttendanceHistory(reduce = reduce)
            }
            AttendHistoryIntent.OnClickBackButton -> {
                sideEffect(AttendHistorySideEffect.NavigateToBack)
            }
        }
    }

    private fun loadAttendanceStatistics(
        reduce: (AttendHistoryState.() -> AttendHistoryState) -> Unit
    ) {
        viewModelScope.launch {
            runCatchingIgnoreCancelled { 
                attendanceRepository.getAttendanceStatistics()
            }.onSuccess {
                reduce {
                    copy(
                        totalSessionCount = it.totalSessionCount,
                        remainingSessionCount = it.remainingSessionCount,
                        sessionProgressRate = it.sessionProgressRate,
                        attendanceCount = it.attendanceCount,
                        attendancePoint = it.attendancePoint,
                        absenceCount = it.absenceCount,
                        lateCount = it.lateCount,
                        latePassCount = it.latePassCount,
                    )
                }
            }.onFailure { error ->
                error.record()
            }
        }
    }
    
    private fun loadAttendanceHistory(
        reduce: (AttendHistoryState.() -> AttendHistoryState) -> Unit
    ) {
        viewModelScope.launch {
            runCatchingIgnoreCancelled { 
                attendanceRepository.getAttendanceHistory()
            }.onSuccess { attendanceHistory ->
                reduce {
                    copy(
                        attendanceHistoryList = attendanceHistory
                    )
                }
            }.onFailure { error ->
                error.record()
            }
        }
    }
}
