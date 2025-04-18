package com.yapp.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class AttendHistoryViewModel @Inject constructor(
    private val attendHistoryUseCase: AttendHistoryUseCase
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
                collectAttendance(reduce = reduce)
            }
            AttendHistoryIntent.OnClickBackButton -> {
                sideEffect(AttendHistorySideEffect.NavigateToBack)
            }
        }
    }

    private fun collectAttendance(
        reduce: (AttendHistoryState.() -> AttendHistoryState) -> Unit
    ) {
        attendHistoryUseCase()
            .catch { error ->
                error.record()
            }.onEach { (attendace, sessions) ->
                reduce {
                    copy(
                        totalSessionCount = attendace.totalSessionCount,
                        remainingSessionCount = attendace.remainingSessionCount,
                        sessionProgressRate = attendace.sessionProgressRate,
                        attendanceCount = attendace.attendanceCount,
                        attendancePoint = attendace.attendancePoint,
                        absenceCount = attendace.absenceCount,
                        lateCount = attendace.lateCount,
                        latePassCount = attendace.latePassCount,
                        sessions = sessions
                    )
                }
            }.launchIn(viewModelScope)
    }
}
