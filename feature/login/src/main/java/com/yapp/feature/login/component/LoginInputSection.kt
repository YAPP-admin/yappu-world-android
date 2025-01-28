package com.yapp.feature.login.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonXLarge
import com.yapp.core.designsystem.component.input.text.YappInputTextLarge
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.PasswordInputTextLarge
import com.yapp.feature.login.R

@Composable
fun LoginInputSection(
    email : String,
    password : String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    buttonEnable : Boolean,
    onClickButton : () -> Unit
) {
    YappInputTextLarge(
        value = email,
        onValueChange = onEmailChange,
        placeholder = stringResource(R.string.login_placeholder_email),
    )
    Spacer(Modifier.height(16.dp))
    PasswordInputTextLarge(
        label = stringResource(R.string.login_title_pw),
        password = password,
        onPasswordChange = onPasswordChange,
        placeholder = stringResource(R.string.login_placeholder_pw)
    )
    Spacer(Modifier.height(24.dp))
    YappSolidPrimaryButtonXLarge(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.login_btn),
        enable = (buttonEnable),
        onClick = onClickButton
    )
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    YappTheme {
        Column {
            LoginInputSection(
                "",
                "",
                {},
                {},
                false,
                {}
            )
        }
    }
}
