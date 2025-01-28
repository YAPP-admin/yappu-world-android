package com.yapp.feature.signup.signup

import androidx.activity.compose.BackHandler
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
import com.yapp.feature.signup.signup.component.SignUpCodeBottomDialog
import com.yapp.feature.signup.signup.page.CompleteContent
import com.yapp.feature.signup.signup.page.PendingContent
import com.yapp.feature.signup.signup.page.email.EmailPage
import com.yapp.feature.signup.signup.page.name.NamePage
import com.yapp.feature.signup.signup.page.password.PasswordPage
import com.yapp.feature.signup.signup.page.position.PositionPage

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
    BackHandler {
        onIntent(SignUpIntent.BackPressed)
    }

    YappBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(WindowInsets.navigationBars)
                .imePadding()
        ) {
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
                        onNameChanged = { onIntent(SignUpIntent.NameChanged(it)) }
                    )

                    SignUpStep.Email -> EmailPage(
                        onEmailChanged = { onIntent(SignUpIntent.EmailChanged(it)) }
                    )

                    SignUpStep.Password -> PasswordPage(
                        onPasswordChanged = { onIntent(SignUpIntent.PasswordChanged(it)) },
                        onPasswordConfirmChanged = { onIntent(SignUpIntent.PasswordConfirmChanged(it)) },
                    )

                    SignUpStep.Position -> PositionPage(
                        name = uiState.name,
                        onActivityUnitsChanged = { onIntent(SignUpIntent.ActivityUnitsChanged(it)) }
                    )

                    SignUpStep.Complete -> CompleteContent()
                    SignUpStep.Pending -> PendingContent()
                }
            }

            if (uiState.showSignUpScreenButton) {
                SignUpScreenButton(
                    uiState = uiState,
                    onIntent = onIntent
                )
            }
        }

        if (uiState.showSignUpCodeBottomDialog) {
            SignUpCodeBottomDialog(
                onDismissRequest = { onIntent(SignUpIntent.DismissSignUpCodeBottomDialog) }
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

    if (isKeyboardVisible.not()) {
        YappSolidPrimaryButtonXLarge(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            enable = uiState.primaryButtonEnable,
            text = stringResource(R.string.signup_screen_button_next),
            onClick = { onIntent(SignUpIntent.ClickPrimaryButton) }
        )
        Spacer(Modifier.height(16.dp))
        return
    }

    if (uiState.showKeyboardAboveButton) {
        YappSolidPrimaryButtonLarge(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(0.dp),
            enable = uiState.primaryButtonEnable,
            text = stringResource(R.string.signup_screen_button_next),
            onClick = { onIntent(SignUpIntent.ClickPrimaryButton) }
        )
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    YappTheme {
        SignUpScreen()
    }
}

