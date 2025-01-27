package com.yapp.feature.signup.signup

import androidx.lifecycle.ViewModel
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
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
            SignUpIntent.ClickBackButton -> {
                val previousStep = when (state.currentStep) {
                    SignUpStep.Pending -> SignUpStep.Complete
                    SignUpStep.Complete -> SignUpStep.Position
                    SignUpStep.Position -> SignUpStep.Password
                    SignUpStep.Password -> SignUpStep.Email
                    SignUpStep.Email -> SignUpStep.Name
                    SignUpStep.Name -> TODO()
                }

                reduce { copy(currentStep = previousStep) }
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

                reduce { copy(currentStep = nextStep) }
            }
        }
    }
}
