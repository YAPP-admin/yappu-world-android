
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
internal fun LoginRouteScreen(
    onClickSignUP: (String) -> Unit,
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
        LoginContent(
            loginState = loginState,
            onSignUpClick = {viewModel.store.onIntent(LoginIntent.ClickSignUpButton)},
            onLoginClick = {viewModel.store.onIntent(LoginIntent.ClickLoginButton)},
            onIDChange = { viewModel.store.onIntent(LoginIntent.InputID(it)) },
            onPWChange = {viewModel.store.onIntent(LoginIntent.InputPW(it))}
        )
    }
}

@Composable
fun LoginContent(
    loginState: LoginState,
    onSignUpClick: () -> Unit,
    onLoginClick : () -> Unit,
    onIDChange: (String) -> Unit,
    onPWChange : (String) -> Unit,
) {
    Column {
        Spacer(Modifier.height(72.dp))
        TopTitle()
        LoginInputSection(
            id = loginState.id,
            pw = loginState.password,
            onIDChange = onIDChange,
            onPWChange = onPWChange,
            isActive = loginState.isActivateLoginButton,
            clickButton = onLoginClick
        )
        Spacer(Modifier.height(24.dp))
        LoginDivider()
        Spacer(Modifier.height(32.dp))
        LoginSignUpSection(onSignUpClick)
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    YappTheme {
        LoginRouteScreen({})
    }
}