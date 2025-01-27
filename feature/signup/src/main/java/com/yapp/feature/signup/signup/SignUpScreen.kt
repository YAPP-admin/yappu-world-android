package com.yapp.feature.signup.signup

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonLarge
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonXLarge
import com.yapp.core.designsystem.component.header.YappHeaderActionbar
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.yappDefaultAnimatedContentTransitionSpec
import com.yapp.core.ui.util.keyboardAsState
import com.yapp.feature.signup.R
import com.yapp.feature.signup.signup.page.CompleteContent
import com.yapp.feature.signup.signup.page.EmailContent
import com.yapp.feature.signup.signup.page.PasswordContent
import com.yapp.feature.signup.signup.page.PendingContent
import com.yapp.feature.signup.signup.page.PositionContent
import com.yapp.feature.signup.signup.page.name.NamePage

@Composable
fun SignUpRoute(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()

    SignUpScreen(
        uiState = uiState,
        onIntent = { viewModel.store.onIntent(it) }
    )
}

@Composable
fun SignUpScreen(
    uiState: SignUpState = SignUpState(),
    onIntent: (SignUpIntent) -> Unit = {},
) {
    YappBackground {
        Column(modifier = Modifier.fillMaxSize()) {
            YappHeaderActionbar(
                title = stringResource(R.string.signup_screen_title),
                leftIcon = com.yapp.core.designsystem.R.drawable.icon_chevron_left,
                onClickLeftIcon = { onIntent(SignUpIntent.ClickBackButton) },
                contentDescription = stringResource(R.string.signup_screen_header_icon_descrption)
            )

            Spacer(Modifier.height(16.dp))

            AnimatedContent(
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Max), // Keyboard 숨김/보여짐 여부에 따라 AnimatedContent의 height가 달라짐 방지
                targetState = uiState.currentStep,
                transitionSpec = {
                    yappDefaultAnimatedContentTransitionSpec(
                        leftDirectionCondition = initialState.ordinal < targetState.ordinal
                    )
                },
                label = "AnimatedContent",
            ) { targetState ->
                when (targetState) {
                    SignUpStep.Name -> NamePage(
                        onChangeName = { onIntent(SignUpIntent.UpdateName(it)) }
                    )
                    SignUpStep.Email -> EmailContent()
                    SignUpStep.Password -> PasswordContent()
                    SignUpStep.Position -> PositionContent()
                    SignUpStep.Complete -> CompleteContent()
                    SignUpStep.Pending -> PendingContent()
                }
            }

            SignUpScreenButton(
                uiState = uiState,
                onIntent = onIntent
            )
        }
    }
}

@Composable
private fun SignUpScreenButton(
    uiState: SignUpState,
    onIntent: (SignUpIntent) -> Unit
) {
    val isKeyboardVisible by keyboardAsState()

    if (isKeyboardVisible) {
        YappSolidPrimaryButtonLarge(
            modifier = Modifier
                .fillMaxWidth()
                .consumeWindowInsets(WindowInsets.navigationBars)
                .imePadding(),
            shape = RoundedCornerShape(0.dp),
            enable = uiState.primaryButtonEnable,
            text = stringResource(R.string.signup_screen_button_next),
            onClick = { onIntent(SignUpIntent.ClickPrimaryButton) }
        )
    } else {
        YappSolidPrimaryButtonXLarge(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .imePadding(),
            enable = uiState.primaryButtonEnable,
            text = stringResource(R.string.signup_screen_button_next),
            onClick = { onIntent(SignUpIntent.ClickPrimaryButton) }
        )

        Spacer(Modifier.height(16.dp))
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    YappTheme {
        SignUpScreen()
    }
}

