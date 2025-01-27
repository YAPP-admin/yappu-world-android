package com.yapp.feature.signup.signup

import androidx.lifecycle.ViewModel
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.model.SignUpInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    private var signUpInfo = SignUpInfo()

    val store: MviIntentStore<SignUpState, SignUpIntent, Nothing> =
        mviIntentStore(
            initialState = SignUpState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: SignUpIntent,
        state: SignUpState,
        reduce: (SignUpState.() -> SignUpState) -> Unit,
        postSideEffect: (Nothing) -> Unit
    ) {
        when (intent) {
            SignUpIntent.BackPressed,
            SignUpIntent.ClickBackButton -> {
                val previousStep = when (state.currentStep) {
                    SignUpStep.Pending -> SignUpStep.Complete
                    SignUpStep.Complete -> SignUpStep.Position
                    SignUpStep.Position -> SignUpStep.Password
                    SignUpStep.Password -> SignUpStep.Email
                    SignUpStep.Email -> SignUpStep.Name
                    SignUpStep.Name -> TODO()
                }

                reduce {
                    copy(
                        currentStep = previousStep,
                        primaryButtonEnable = updatePrimaryButtonEnable(previousStep)
                    )
                }
            }

            SignUpIntent.ClickPrimaryButton -> {
                val nextStep = when (state.currentStep) {
                    SignUpStep.Name -> SignUpStep.Email
                    SignUpStep.Email -> SignUpStep.Password
                    SignUpStep.Password -> SignUpStep.Position
                    SignUpStep.Position -> SignUpStep.Complete // TODO SignUpStep.Pending
                    SignUpStep.Complete -> TODO()
                    SignUpStep.Pending -> SignUpStep.Pending
                }

                reduce {
                    copy(
                        currentStep = nextStep,
                        primaryButtonEnable = updatePrimaryButtonEnable(nextStep)
                    )
                }
            }

            is SignUpIntent.NameChanged -> {
                signUpInfo = signUpInfo.copy(name = intent.name)
                reduce { copy(primaryButtonEnable = intent.name.isNotBlank()) }
            }

            is SignUpIntent.EmailChanged -> {
                signUpInfo = signUpInfo.copy(email = intent.email)
                reduce { copy(primaryButtonEnable = intent.email.isNotBlank()) } // TODO 이메일 정규식 검사
            }
        }
    }

    private fun updatePrimaryButtonEnable(step: SignUpStep): Boolean {
        return when (step) {
            SignUpStep.Name -> signUpInfo.name.isNotBlank()
            SignUpStep.Email -> signUpInfo.email.isNotBlank() // TODO 이메일 정규식 검사
            SignUpStep.Password -> TODO()
            SignUpStep.Position -> TODO()
            SignUpStep.Complete -> TODO()
            SignUpStep.Pending -> TODO()
        }
    }
}
