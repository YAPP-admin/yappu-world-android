package com.yapp.feature.signup.signup

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonXLarge
import com.yapp.core.designsystem.component.header.YappHeaderActionbar
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.feature.signup.R
import com.yapp.feature.signup.signup.content.EmailContent
import com.yapp.feature.signup.signup.content.NameContent

@Composable
fun SignUpRoute() {
    SignUpScreen()
}

@Composable
fun SignUpScreen(
) {
    YappBackground {
        Column {
            YappHeaderActionbar(
                title = stringResource(R.string.signup_screen_title),
                leftIcon = com.yapp.core.designsystem.R.drawable.icon_chevron_left,
                onClickLeftIcon = {}
            )

            Spacer(Modifier.height(16.dp))

            AnimatedContent(
                modifier = Modifier.weight(1f),
                targetState = SignUpStep.Name,
                label = "AnimatedContent",
            ) { targetState ->
                when (targetState) {
                    SignUpStep.Name -> NameContent()
                    SignUpStep.Email -> EmailContent()
                    SignUpStep.Password -> Unit
                    SignUpStep.Position -> Unit
                    SignUpStep.Complete -> Unit
                }
            }

            YappSolidPrimaryButtonXLarge(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.signup_screen_button_next),
                onClick = {}
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    YappTheme {
        SignUpScreen()
    }
}

