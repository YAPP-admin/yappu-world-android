package com.yapp.feature.signup.signup.page.name

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.input.text.YappInputTextLarge
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.signup.R

@Composable
fun NamePage(
    viewModel: NameViewModel = hiltViewModel(),
    onChangeName: (String) -> Unit,
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    viewModel.store.sideEffects.collectWithLifecycle {
        when (it) {
            is NameSideEffect.ChangeName -> onChangeName(it.name)
        }
    }

    NameContent(
        uiState = uiState,
        onIntent = { viewModel.store.onIntent(it) },
    )
}

@Composable
private fun NameContent(
    uiState: NameState = NameState(),
    onIntent: (NameIntent) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.signup_screen_step_1),
            style = YappTheme.typography.caption2Bold,
            color = YappTheme.colorScheme.primaryNormal,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.signup_screen_name_title),
            style = YappTheme.typography.title3Bold,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.signup_screen_name_description),
            style = YappTheme.typography.body2NormalMedium,
            color = YappTheme.colorScheme.labelAlternative,
        )

        Spacer(Modifier.height(40.dp))

        YappInputTextLarge(
            label = stringResource(R.string.signup_screen_name_input_text_label),
            placeholder = stringResource(R.string.signup_screen_name_input_text_placeholder),
            value = uiState.name,
            onValueChange = { onIntent(NameIntent.ChangeName(it)) },
        )
    }
}

@Preview
@Composable
private fun NameContentPreview() {
    YappTheme {
        YappBackground {
            NameContent()
        }
    }
}