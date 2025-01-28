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
                        primaryButtonEnable = getPrimaryButtonEnable(previousStep)
                    )
                }
            }

            SignUpIntent.ClickPrimaryButton -> {
                if (state.currentStep == SignUpStep.Position) {
                    reduce { copy(showSignUpCodeBottomDialog = true) }
                    return
                }

                val nextStep = when (state.currentStep) {
                    SignUpStep.Name -> SignUpStep.Email
                    SignUpStep.Email -> SignUpStep.Password
                    SignUpStep.Password -> SignUpStep.Position
                    SignUpStep.Position -> SignUpStep.Position
                    SignUpStep.Complete -> TODO()
                    SignUpStep.Pending -> SignUpStep.Pending
                }

                reduce {
                    copy(
                        currentStep = nextStep,
                        primaryButtonEnable = getPrimaryButtonEnable(nextStep)
                    )
                }
            }

            is SignUpIntent.NameChanged -> {
                signUpInfo = signUpInfo.copy(name = intent.name)
                reduce {
                    copy(
                        primaryButtonEnable = intent.name.isNotBlank(),
                        name = intent.name
                    )
                }
            }

            is SignUpIntent.EmailChanged -> {
                signUpInfo = signUpInfo.copy(email = intent.email)
                reduce { copy(primaryButtonEnable = intent.email.isNotBlank()) } // TODO 이메일 정규식 검사
            }

            is SignUpIntent.PasswordChanged -> {
                signUpInfo = signUpInfo.copy(password = intent.password)
                reduce { copy(primaryButtonEnable = signUpInfo.isAllPasswordConditionValid) }
            }

            is SignUpIntent.PasswordConfirmChanged -> {
                signUpInfo = signUpInfo.copy(passwordConfirm = intent.passwordConfirm)
                reduce { copy(primaryButtonEnable = signUpInfo.isAllPasswordConditionValid) }
            }

            is SignUpIntent.ActivityUnitsChanged -> {
                signUpInfo = signUpInfo.copy(activityUnits = intent.activityUnits)
                reduce { copy(primaryButtonEnable = signUpInfo.isActivityUnitsValid) }
            }

            SignUpIntent.DismissSignUpCodeBottomDialog -> reduce { copy(showSignUpCodeBottomDialog = false) }
        }
    }

    private fun getPrimaryButtonEnable(step: SignUpStep): Boolean {
        return when (step) {
            SignUpStep.Name -> signUpInfo.name.isNotBlank()
            SignUpStep.Email -> signUpInfo.email.isNotBlank() // TODO 이메일 정규식 검사
            SignUpStep.Password -> signUpInfo.isAllPasswordConditionValid
            SignUpStep.Position -> signUpInfo.isActivityUnitsValid
            SignUpStep.Complete -> TODO()
            SignUpStep.Pending -> TODO()
        }
    }
}
