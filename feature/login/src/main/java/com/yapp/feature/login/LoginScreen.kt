package com.yapp.feature.login

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.ui.R as coreR
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.core.ui.extension.safeOpenUri
import com.yapp.feature.login.component.AgreementBottomDialog
import com.yapp.feature.login.component.LoginDivider
import com.yapp.feature.login.component.LoginInputSection
import com.yapp.feature.login.component.LoginSignUpSection
import com.yapp.feature.login.component.TopTitle


@Composable
internal fun LoginRoute(
    navigateToSignupName: () -> Unit,
    navigateToSignupPending: () -> Unit,
    navigateToSignupReject: () -> Unit,
    navigateToHome: () -> Unit,
    handleException: (Throwable) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        viewModel.store.onIntent(LoginIntent.EnterLoginScreen)
    }

    viewModel.store.sideEffects.collectWithLifecycle { effect ->
        when (effect) {
            LoginSideEffect.NavigateToSignUp -> navigateToSignupName()
            is LoginSideEffect.OpenWebBrowser -> {
                uriHandler.safeOpenUri(effect.link)
            }

            LoginSideEffect.NavigateToHome -> navigateToHome()
            is LoginSideEffect.HandleException -> handleException(effect.exception)

            LoginSideEffect.NavigateToSignUpPending -> navigateToSignupPending()
            LoginSideEffect.NavigateToSignUpReject -> navigateToSignupReject()
            LoginSideEffect.ShowUrlLoadFailToast -> {
                Toast.makeText(
                    context,
                    context.getString(coreR.string.toast_message_error_loading_url),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    LoginScreen(
        loginState = uiState,
        onIntent = { viewModel.store.onIntent(it) }
    )
}

@Composable
fun LoginScreen(
    loginState: LoginState,
    onIntent: (LoginIntent) -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

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
                onEmailChange = { onIntent(LoginIntent.ChangeEmail(it)) },
                onPasswordChange = { onIntent(LoginIntent.ChangePassword(it)) },
                buttonEnable = loginState.enableLoginButton,
                onButtonClick = {
                    keyboardController?.hide()
                    onIntent(LoginIntent.ClickLoginButton)
                },
                emailErrorDescription = loginState.emailErrorDescription,
                passwordErrorDescription = loginState.passwordErrorDescription
            )
            Spacer(Modifier.height(24.dp))
            LoginDivider()
            Spacer(Modifier.height(32.dp))
            LoginSignUpSection { onIntent(LoginIntent.ClickSignUpButton) }
        }
        if (loginState.showAgreementDialog) {
            AgreementBottomDialog(
                onDismiss = { onIntent(LoginIntent.CloseAgreementDialog) },
                terms = loginState.terms,
                personalPolicy = loginState.personalPolicy,
                onTermsChecked = { onIntent(LoginIntent.CheckTerms(it)) },
                onPersonalPolicyChecked = { onIntent(LoginIntent.CheckPersonalPolicy(it)) },
                enableNextButton = loginState.enableNextButton,
                onNextButtonClick = { onIntent(LoginIntent.ClickNextButton) },
                onTermsButtonClick = { onIntent(LoginIntent.ClickTerms) },
                onPersonalPolicyButtonClick = { onIntent(LoginIntent.ClickPersonalPolicy) }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    YappTheme {
        LoginScreen(
            loginState = LoginState(),
            onIntent = {},
        )
    }
}