package com.yapp.feature.history.attend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.header.YappHeaderActionbar
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.feature.history.R
import com.yapp.feature.history.attend.components.AttendanceStatusSection
import com.yapp.feature.history.attend.components.StatusItem
import com.yapp.model.ScheduleInfo
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.yapp.feature.history.attend.AttendHistoryIntent as Intent
import com.yapp.feature.history.attend.AttendHistorySideEffect as SideEffect
import com.yapp.core.designsystem.R as coreDesignR

@Composable
internal fun AttendHistoryRoute(
    viewModel: AttendHistoryViewModel = hiltViewModel(),
    navigateToBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.store.onIntent(Intent.OnEntryScreen) }

    AttendHistoryScreen(
        attendancePoint = uiState.attendancePoint,
        sessionProgressRate = uiState.sessionProgressRate,
        totalSessionCount = uiState.totalSessionCount,
        totalRemainSessionCount = uiState.remainingSessionCount,
        attendance = uiState.attendanceCount,
        late =  uiState.lateCount,
        absence = uiState.absenceCount,
        latePass = uiState.latePassCount,
        sessions = uiState.sessions,
        onClickBackButton = {
            viewModel.store.onIntent(Intent.OnClickBackButton)
        }
    )

    LaunchedEffect(viewModel.store.sideEffects) {
        viewModel.store.sideEffects.onEach { effect ->
            when(effect) {
                SideEffect.NavigateToBack -> {
                    navigateToBack()
                }
            }
        }.launchIn(scope)
    }
}

@Composable
private fun AttendHistoryScreen(
    attendancePoint: Int,
    sessionProgressRate: Int,
    totalSessionCount: Int,
    totalRemainSessionCount: Int,
    attendance: Int,
    late: Int,
    absence: Int,
    latePass: Int,
    sessions: List<ScheduleInfo>,
    onClickBackButton: () -> Unit
) {
    YappTheme {
        Column {
            YappHeaderActionbar(
                title = stringResource(R.string.attendance_title),
                leftIcon = coreDesignR.drawable.icon_chevron_left,
                onClickLeftIcon = onClickBackButton
            )
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Spacer(modifier = Modifier.height(20.dp))
                AttendanceStatusSection(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = stringResource(R.string.attendance_my_status),
                    subTitle = stringResource(R.string.attendance_total_score),
                    totalRate = "${attendancePoint}점",
                    slot = {
                        StatusItem(modifier = Modifier.weight(1f), title = stringResource(R.string.attendance_item), count = attendance)
                        StatusItem(modifier = Modifier.weight(1f), title = stringResource(R.string.late_item), count = late)
                        StatusItem(modifier = Modifier.weight(1f), title = stringResource(R.string.absence_item), count = absence)
                        StatusItem(modifier = Modifier.weight(1f), title = stringResource(R.string.latePass_item), count = latePass)
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(thickness = 12.dp, color = YappTheme.colorScheme.lineNormalAlternative)
                Spacer(modifier = Modifier.height(24.dp))
                AttendanceStatusSection(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = stringResource(R.string.session_title),
                    subTitle = stringResource(R.string.total_remain_session_progress_rate_title),
                    totalRate = "${sessionProgressRate}%",
                    slot = {
                        StatusItem(
                            modifier = Modifier.weight(1f),
                            title = stringResource(R.string.total_session_count_title),
                            count = totalSessionCount
                        )
                        StatusItem(
                            modifier = Modifier.weight(1f),
                            title = stringResource(R.string.total_remain_session_count_title),
                            count = totalRemainSessionCount
                        )
                    }
                )
            }
        }
    }
}
