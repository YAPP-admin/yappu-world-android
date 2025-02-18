package com.yapp.feature.login

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.constant.Url
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.login.component.AgreementBottomDialog
import com.yapp.feature.login.component.LoginDivider
import com.yapp.feature.login.component.LoginInputSection
import com.yapp.feature.login.component.LoginSignUpSection
import com.yapp.feature.login.component.TopTitle


@Composable
internal fun LoginRoute(
    navigateToSignup: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    viewModel.store.sideEffects.collectWithLifecycle { effect ->
        when (effect) {
            LoginSideEffect.NavigateToSignUp -> navigateToSignup()
            LoginSideEffect.ShowTerms -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Url.TERMS))
                context.startActivity(intent)
            }

            LoginSideEffect.ShowPersonalPolicy -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Url.PRIVACY_POLICY))
                context.startActivity(intent)
            }

            LoginSideEffect.NavigateToHome -> navigateToHome()
            is LoginSideEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
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
        LoginRoute({}, {})
    }
}