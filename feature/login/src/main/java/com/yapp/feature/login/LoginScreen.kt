package com.yapp.feature.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.login.component.AgreementBottomDialog
import com.yapp.feature.login.component.LoginDivider
import com.yapp.feature.login.component.LoginInputSection
import com.yapp.feature.login.component.LoginSignUpSection
import com.yapp.feature.login.component.TopTitle


@Composable
internal fun LoginRoute(
    navigateToSignup: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val loginState by viewModel.store.uiState.collectAsStateWithLifecycle()
    viewModel.store.sideEffects.collectWithLifecycle { effect ->
        when (effect) {
            LoginSideEffect.NavigateToSignUp -> navigateToSignup()
        }
    }

    LoginScreen(
        loginState = loginState,
        onIntent = { viewModel.store.onIntent(it) }
    )
}

@Composable
fun LoginScreen(
    loginState: LoginState,
    onIntent: (LoginIntent) -> Unit = {}
) {
    YappBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(72.dp))
            TopTitle()
            LoginInputSection(
                email = loginState.email,
                password = loginState.password,
                onEmailChange = { onIntent(LoginIntent.EmailChanged(it)) },
                onPasswordChange = { onIntent(LoginIntent.PasswordChanged(it)) },
                buttonEnable = loginState.enableLoginButton,
                onClickButton = { onIntent(LoginIntent.ClickLoginButton) }
            )
            Spacer(Modifier.height(24.dp))
            LoginDivider()
            Spacer(Modifier.height(32.dp))
            LoginSignUpSection { onIntent(LoginIntent.ClickSignUpButton) }
        }
        if (loginState.showAgreementDialog) {
            AgreementBottomDialog(
                onDismiss = { onIntent(LoginIntent.CloseAgreementDialog) },
                agreement1 = loginState.agreement1,
                agreement2 = loginState.agreement2,
                onAgreement1Checked = { onIntent(LoginIntent.CheckAgreement1(it)) },
                onAgreement2Checked = { onIntent(LoginIntent.CheckAgreement2(it)) },
                enableNextButton = loginState.enableNextButton,
                onClickNextButton = { onIntent(LoginIntent.ClickNextButton)}
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    YappTheme {
        LoginRoute({})
    }
}