package com.yapp.feature.signup.signup.page.email

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
fun EmailPage(
    viewModel: EmailViewModel = hiltViewModel(),
    onChangeEmail: (String) -> Unit,
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    viewModel.store.sideEffects.collectWithLifecycle {
        when (it) {
            is EmailSideEffect.ChangeEmail -> onChangeEmail(it.email)
        }
    }

    EmailContent(
        uiState = uiState,
        onIntent = { viewModel.store.onIntent(it) },
    )
}

@Composable
fun EmailContent(
    uiState: EmailState = EmailState(),
    onIntent: (EmailIntent) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.signup_screen_step_2),
            style = YappTheme.typography.caption2Bold,
            color = YappTheme.colorScheme.primaryNormal,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.signup_screen_email_title),
            style = YappTheme.typography.title3Bold,
        )

        Spacer(Modifier.height(40.dp))

        YappInputTextLarge(
            label = stringResource(R.string.signup_screen_email_input_text_label),
            placeholder = stringResource(R.string.signup_screen_email_input_placeholder),
            value = uiState.email,
            onValueChange = { onIntent(EmailIntent.ChangeEmail(it)) },
        )
    }
}

@Preview
@Composable
fun EmailContentPreview() {
    YappTheme {
        YappBackground {
            EmailContent()
        }
    }
}