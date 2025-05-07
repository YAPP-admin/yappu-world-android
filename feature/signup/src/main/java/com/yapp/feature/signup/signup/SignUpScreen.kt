package com.yapp.feature.signup.signup

import android.widget.Toast
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
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonLarge
import com.yapp.core.designsystem.component.button.solid.YappSolidPrimaryButtonXLarge
import com.yapp.core.designsystem.component.button.text.YappTextAssistiveButtonMedium
import com.yapp.core.designsystem.component.header.YappHeaderActionbar
import com.yapp.core.designsystem.theme.YappTheme
import com.yapp.core.ui.component.YappBackground
import com.yapp.core.ui.extension.collectWithLifecycle
import com.yapp.core.ui.extension.safeOpenUri
import com.yapp.core.ui.util.keyboardAsState
import com.yapp.feature.signup.R
import com.yapp.core.ui.R as coreR
import com.yapp.feature.signup.signup.component.SignUpCodeBottomDialog
import com.yapp.feature.signup.signup.extension.signUpAnimatedContentTransitionSpec
import com.yapp.feature.signup.signup.page.CompletePage
import com.yapp.feature.signup.signup.page.PendingPage
import com.yapp.feature.signup.signup.page.RejectPage
import com.yapp.feature.signup.signup.page.email.EmailPage
import com.yapp.feature.signup.signup.page.name.NamePage
import com.yapp.feature.signup.signup.page.password.PasswordPage
import com.yapp.feature.signup.signup.page.position.PositionPage

@Composable
fun SignUpRoute(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateHome: () -> Unit,
    handleException: (Throwable) -> Unit,
) {
    val uiState by viewModel.store.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    viewModel.store.sideEffects.collectWithLifecycle {
        when (it) {
            SignUpSideEffect.NavigateBack -> navigateBack()
            SignUpSideEffect.ClearFocus -> focusManager.clearFocus(force = true)
            SignUpSideEffect.NavigateHome -> navigateHome()
            is SignUpSideEffect.OpenWebBrowser -> uriHandler.safeOpenUri(it.link)
            is SignUpSideEffect.ShowUrlLoadFailToast -> {
                Toast.makeText(
                    context,
                    context.getString(coreR.string.toast_message_error_loading_url),
                    Toast.LENGTH_SHORT
                ).show()
            }

            is SignUpSideEffect.HandleException -> handleException(it.exception)
        }
    }

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

    LaunchedEffect(Unit) {
        onIntent(SignUpIntent.EnterScreen)
    }

    YappBackground(
        contentWindowInsets = WindowInsets.systemBars,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(WindowInsets.navigationBars)
                .imePadding()
        ) {
            YappHeaderActionbar(
                title = stringResource(R.string.signup_screen_title),
                leftIcon = uiState.backIcon,
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
                    signUpAnimatedContentTransitionSpec(
                        initialStep = initialState,
                        targetStep = targetState,
                    )
                },
                label = "AnimatedContent",
            ) { targetState ->
                when (targetState) {
                    SignUpStep.Name -> NamePage(
                        onNameChanged = { onIntent(SignUpIntent.NameChanged(it)) }
                    )

                    SignUpStep.Email -> EmailPage(
                        onEmailChanged = { email, verified ->
                            onIntent(
                                SignUpIntent.EmailChanged(
                                    email,
                                    verified
                                )
                            )
                        },
                        handleException = { onIntent(SignUpIntent.HandleException(it)) },
                    )

                    SignUpStep.Password -> PasswordPage(
                        onPasswordChanged = { onIntent(SignUpIntent.PasswordChanged(it)) },
                        onPasswordConfirmChanged = { onIntent(SignUpIntent.PasswordConfirmChanged(it)) },
                    )

                    SignUpStep.Position -> PositionPage(
                        name = uiState.name,
                        positions = uiState.positions,
                        onActivityUnitsChanged = { onIntent(SignUpIntent.ActivityUnitsChanged(it)) }
                    )

                    SignUpStep.Complete -> CompletePage()
                    SignUpStep.Pending -> PendingPage()
                    SignUpStep.Reject -> RejectPage()
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
                onDismissRequest = { onIntent(SignUpIntent.DismissSignUpCodeBottomDialog) },
                signUpCode = uiState.signUpCode,
                inputCompleteButtonEnable = uiState.inputCompleteButtonEnable,
                isSignUpCodeInputTextError = uiState.isSignUpErrorInputTextError,
                signUpCodeInputTextDescription = uiState.signUpErrorInputTextDescription,
                onSignUpCodeChange = { onIntent(SignUpIntent.ChangeSignUpCode(it)) },
                onInputCompleteButtonClick = { onIntent(SignUpIntent.ClickInputCompleteButton) },
                onNoSignUpCodeButtonClick = { onIntent(SignUpIntent.ClickNoSignUpCodeButton) },
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

    if (uiState.showPendingButton) {
        YappTextAssistiveButtonMedium(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.signup_screen_pending_assistive_button),
            onClick = { onIntent(SignUpIntent.ClickPendingButton) }
        )
        Spacer(Modifier.height(20.dp))
        return
    }

    if (uiState.showRejectButton) {
        YappTextAssistiveButtonMedium(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.signup_screen_reject_assistive_button),
            onClick = { onIntent(SignUpIntent.ClickPendingButton) }
        )
        Spacer(Modifier.height(20.dp))
        return
    }

    if (isKeyboardVisible.not()) {
        YappSolidPrimaryButtonXLarge(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            enable = uiState.primaryButtonEnable,
            text = stringResource(uiState.primaryButtonText),
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
        SignUpScreen(
            uiState = SignUpState(currentStep = SignUpStep.Pending)
        )
    }
}

