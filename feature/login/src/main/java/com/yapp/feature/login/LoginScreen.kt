
package com.yapp.feature.login

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.login.component.LoginDivider
import com.yapp.feature.login.component.LoginInputSection
import com.yapp.feature.login.component.LoginSignUpSection
import com.yapp.feature.login.component.TopTitle


@Composable
internal fun LoginRoute(
    onClickSignUp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val loginState by viewModel.store.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    viewModel.store.sideEffects.collectWithLifecycle { effect ->
        when (effect) {
            is LoginSideEffect.ShowSignUpDialog -> {}
            is LoginSideEffect.ShowToast -> { Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show() }
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.TopStart
    ) {
        LoginScreen(
            loginState = loginState,
            onIntent = { viewModel.store.onIntent(it) },
        )
    }
}

@Composable
fun LoginScreen(
    loginState: LoginState,
    onIntent: (LoginIntent) -> Unit = {}
) {
    Column {
        Spacer(Modifier.height(72.dp))
        TopTitle()
        LoginInputSection(
            email = loginState.email,
            password = loginState.password,
            onEmailChange = {onIntent(LoginIntent.EmailChanged(it))},
            onPasswordChange =  {onIntent(LoginIntent.PasswordChanged(it))},
            buttonEnable = loginState.enableLoginButton,
            onClickButton = { onIntent(LoginIntent.ClickLoginButton) }
        )
        Spacer(Modifier.height(24.dp))
        LoginDivider()
        Spacer(Modifier.height(32.dp))
        LoginSignUpSection { onIntent(LoginIntent.ClickSignUpButton) }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    YappTheme {
        LoginRoute({})
    }
}