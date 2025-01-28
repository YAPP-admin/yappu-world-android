package com.yapp.feature.signup.signup.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonLarge
import com.yapp.core.designsystem.component.button.text.YappTextPrimaryButtonSmall
import com.yapp.core.designsystem.component.input.text.YappInputTextLarge
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.BottomDialog
import com.yapp.feature.signup.R

@Composable
fun SignUpCodeBottomDialog(
    signUpCode: String,
    inputCompleteButtonEnable: Boolean,
    isSignUpCodeInputTextError: Boolean,
    signUpCodeInputTextDescription: String?,
    onDismissRequest: () -> Unit,
    onSignUpCodeChange: (String) -> Unit,
    onInputCompleteButtonClick: () -> Unit,
    onNoSignUpCodeButtonClick: () -> Unit
) {
    BottomDialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
        ) {
            Text(
                text = stringResource(R.string.signup_code_bottom_dialog_title),
                style = YappTheme.typography.headline1Bold,
                color = YappTheme.colorScheme.labelNormal
            )

            Spacer(Modifier.height(24.dp))

            YappInputTextLarge(
                label = stringResource(R.string.signup_code_bottom_dialog_input_text_label),
                value = signUpCode,
                placeholder = stringResource(R.string.signup_code_bottom_dialog_input_text_placeholder),
                isError = isSignUpCodeInputTextError,
                description = signUpCodeInputTextDescription,
                onValueChange = onSignUpCodeChange
            )

            Spacer(Modifier.height(24.dp))

            YappSolidPrimaryButtonLarge(
                modifier = Modifier.fillMaxWidth(),
                enable = inputCompleteButtonEnable,
                text = stringResource(R.string.signup_code_bottom_dialog_complete_button),
                onClick = onInputCompleteButtonClick
            )

            Spacer(Modifier.height(8.dp))

            YappTextPrimaryButtonSmall(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.signup_code_bottom_dialog_no_code_button),
                onClick = onNoSignUpCodeButtonClick
            )
        }
    }
}