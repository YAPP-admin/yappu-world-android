package com.yapp.feature.signup.signup.page.password

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
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.PasswordInputTextLarge
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.signup.R

@Composable
fun PasswordPage(
    viewModel: PasswordViewModel = hiltViewModel(),
    onPasswordChanged: (String) -> Unit,
    onPasswordConfirmChanged: (String) -> Unit,
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    viewModel.store.sideEffects.collectWithLifecycle {
        when (it) {
            is PasswordSideEffect.PasswordChanged -> onPasswordChanged(it.password)
            is PasswordSideEffect.PasswordConfirmChanged -> onPasswordConfirmChanged(it.passwordConfirm)
        }
    }

    PasswordContent(
        uiState = uiState,
        onIntent = { viewModel.store.onIntent(it) },
    )
}

@Composable
fun PasswordContent(
    uiState: PasswordState = PasswordState(),
    onIntent: (PasswordIntent) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.signup_screen_step_3),
            style = YappTheme.typography.caption2Bold,
            color = YappTheme.colorScheme.primaryNormal,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.signup_screen_password_title),
            style = YappTheme.typography.title3Bold,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.signup_screen_password_description),
            style = YappTheme.typography.body2NormalMedium,
            color = YappTheme.colorScheme.labelAlternative,
        )

        Spacer(Modifier.height(40.dp))

        PasswordInputTextLarge(
            label = stringResource(R.string.signup_screen_password_input_text_label),
            placeholder = stringResource(R.string.signup_screen_password_input_text_placeholder),
            password = uiState.password,
            onPasswordChange = { onIntent(PasswordIntent.ChangePassword(it)) },
            isError = uiState.isPasswordError,
            description = uiState.passwordErrorDescription,
        )

        Spacer(Modifier.height(24.dp))

        PasswordInputTextLarge(
            label = stringResource(R.string.signup_screen_password_confirm_input_text_label),
            placeholder = stringResource(R.string.signup_screen_password_confirm_input_text_placeholder),
            password = uiState.passwordConfirm,
            onPasswordChange = { onIntent(PasswordIntent.ChangePasswordConfirm(it)) },
            isError = uiState.isPasswordConfirmError,
            description = uiState.passwordConfirmErrorDescription,
        )
    }
}

@Preview
@Composable
private fun PasswordContentPreview() {
    YappTheme {
        YappBackground {
            PasswordContent()
        }
    }
}