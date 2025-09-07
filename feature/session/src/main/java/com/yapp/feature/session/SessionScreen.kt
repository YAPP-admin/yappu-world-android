package com.yapp.feature.session

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun SessionRoute(
    viewModel: SessionViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.store.onIntent(SessionIntent.EnterSessionScreen)
    }
    val state by viewModel.store.uiState.collectAsStateWithLifecycle()
    SessionScreen(state)
}

@Composable
fun SessionScreen(
    state: SessionState,
) {
    if (state.isLoading) {
        CircularProgressIndicator()
    } else {
        Text(text = "Session Screen")
    }
}
