
package com.yapp.feature.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonXLarge
import com.yapp.core.designsystem.component.button.text.YappTextPrimaryButtonSmall
import com.yapp.core.designsystem.component.input.text.YappInputTextLarge
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.feature.login.component.HorizontalDivider
import com.yapp.feature.login.component.TopTitle


@Composable
internal fun LoginRouteScreen(
    onSignUpClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel()
) {
    val loginState by viewModel.store.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    viewModel.store.sideEffects.collectWithLifecycle { effect ->
        when (effect) {
            is LoginSideEffect.ShowSignUpDialog -> {  }
            is LoginSideEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
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
            viewModel = viewModel
        )
    }
}


@Composable
fun LoginContent(
    loginState : LoginState,
    viewModel : LoginViewModel
) {
    when(loginState){
        LoginState.Loading -> LoginLoading()
        LoginState.LoginError -> {}
        LoginState.LoginSuccess -> {
            LoginScreen(loginState,viewModel)
        }
    }
}


@Stable
@Composable
fun LoginScreen(
    loginState : LoginState,
    viewModel : LoginViewModel
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column {
        TopTitle()
        YappInputTextLarge(
            value = username,
            onValueChange = {username = it},
            placeholder = stringResource(R.string.login_placeholder_email),
        )
        Spacer(Modifier.height(16.dp))
        YappInputTextLarge(
            label = stringResource(R.string.login_title_pw),
            password = password,
            onPasswordChange = { password = it },
            placeholder = stringResource(R.string.login_placeholder_pw)
        )
        Spacer(Modifier.height(24.dp))
        YappSolidPrimaryButtonXLarge(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.login_btn),
            enable = (password.isNotEmpty() && username.isNotEmpty()),
            onClick = {
                viewModel.store.onIntent(LoginIntent.Login(username,password))
            }
        )
        HorizontalDivider()
        Spacer(Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = stringResource(R.string.login_title_signup),
                color = YappTheme.colorScheme.labelNormal,
                style = YappTheme.typography.label1NormalRegular,
            )
            YappTextPrimaryButtonSmall(
                text = stringResource(R.string.login_btn_signup),
                contentPaddings = PaddingValues(0.dp),
                enable = true,
                onClick = { viewModel.store.onIntent(LoginIntent.ShowSignUpDialog) }
            )
        }
    }

}

@Composable
private fun LoginLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}


@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    YappTheme {
        LoginRouteScreen({})
    }
}