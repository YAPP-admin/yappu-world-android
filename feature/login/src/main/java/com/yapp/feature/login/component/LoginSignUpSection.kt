package com.yapp.feature.login.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.text.YappTextPrimaryButtonSmall
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.feature.login.R


@Stable
@Composable
fun LoginSignUpSection(
    onClickSignUp : () -> Unit
){
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
            onClick = onClickSignUp
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    YappTheme {
        LoginSignUpSection({})
    }
}