package com.yapp.feature.history.previous

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.header.YappHeaderActionbar
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.history.R
import com.yapp.feature.history.previous.PreviousHistorySideEffect
import com.yapp.feature.history.previous.PreviousHistoryIntent as Intent
import com.yapp.feature.history.previous.PreviousHistorySideEffect as SideEffect
import com.yapp.feature.history.previous.PreviousHistoryState.History

@Composable
internal fun PreviousHistoryRoute(
    viewModel: PreviousHistoryViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
    navigateToLogin: () -> Unit,
    handleException: (Throwable) -> Unit,
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.store.onIntent(Intent.OnEntryScreen)
    }

    viewModel.store.sideEffects.collectWithLifecycle { effect ->
        when (effect) {
            SideEffect.Finish -> {
                navigateToBack()
            }

            is PreviousHistorySideEffect.HandleException -> handleException(effect.exception)
            PreviousHistorySideEffect.NavigateLogin -> navigateToLogin()
        }
    }

    BackHandler { viewModel.store.onIntent(Intent.OnClickBackButton) }

    PreviousHistoryScreen(
        items = uiState.items,
        onClickBackButton = navigateToBack
    )
}


@Composable
private fun PreviousHistoryScreen(
    items: List<History>,
    onClickBackButton: () -> Unit
) {
    YappBackground {
        Column {
            YappHeaderActionbar(
                title = stringResource(R.string.previous_title),
                leftIcon = com.yapp.core.designsystem.R.drawable.icon_chevron_left,
                onClickLeftIcon = onClickBackButton
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items) { item ->
                    HistoryItems(
                        modifier = Modifier.background(
                            color = YappTheme.colorScheme.orange99,
                            shape = RoundedCornerShape(12.dp)
                        ).padding(16.dp),
                        position = item.position,
                        generation = item.generation,
                        slot = {
                            if (item.showSlot) {
                                Text("${item.activityStartDate} - ${item.activityEndDate}")
                            }
                        }
                    )
                }
            }
        }
    }
}

