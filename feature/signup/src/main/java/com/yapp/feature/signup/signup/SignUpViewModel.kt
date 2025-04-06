package com.yapp.feature.signup.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.OperationsRepository
import com.yapp.domain.GetPositionConfigsUseCase
import com.yapp.domain.SignUpUseCase
import com.yapp.model.SignUpInfo
import com.yapp.model.SignUpResult
import com.yapp.model.exceptions.SignUpCodeException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sign

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val getPositionConfigsUseCase: GetPositionConfigsUseCase,
    private val operationsRepository: OperationsRepository,
) : ViewModel() {
    private var signUpInfo = SignUpInfo()
    private var inquiryLink = ""

    val store: MviIntentStore<SignUpState, SignUpIntent, SignUpSideEffect> =
        mviIntentStore(
            initialState = SignUpState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: SignUpIntent,
        state: SignUpState,
        reduce: (SignUpState.() -> SignUpState) -> Unit,
        postSideEffect: (SignUpSideEffect) -> Unit,
    ) {
        when (intent) {
            SignUpIntent.EnterScreen -> {
                getPositionConfigsUseCase()
                    .onEach {
                        reduce { copy(positions = it) }
                    }
                    .catch {
                        it.record()
                    }
                    .launchIn(viewModelScope)

                operationsRepository.getUsageInquiryLink()
                    .onEach {
                        inquiryLink = it
                    }
                    .catch { it.record() }
                    .launchIn(viewModelScope)
            }

            SignUpIntent.BackPressed,
            SignUpIntent.ClickBackButton -> {
                val previousStep = when (state.currentStep) {
                    SignUpStep.Position -> SignUpStep.Password
                    SignUpStep.Password -> SignUpStep.Email
                    SignUpStep.Email -> SignUpStep.Name
                    SignUpStep.Complete,
                    SignUpStep.Pending,
                    SignUpStep.Name -> {
                        postSideEffect(SignUpSideEffect.NavigateBack)
                        return
                    }
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

                if (state.currentStep == SignUpStep.Complete) {
                    postSideEffect(SignUpSideEffect.NavigateHome)
                    return
                }

                val nextStep = when (state.currentStep) {
                    SignUpStep.Name -> SignUpStep.Email
                    SignUpStep.Email -> SignUpStep.Password
                    SignUpStep.Password -> SignUpStep.Position
                    SignUpStep.Position,
                    SignUpStep.Complete,
                    SignUpStep.Pending -> return
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
                signUpInfo = signUpInfo.copy(email = intent.email, isEmailVerified = intent.verified)
                reduce { copy(primaryButtonEnable = intent.verified) }
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

            SignUpIntent.DismissSignUpCodeBottomDialog -> {
                signUpInfo = signUpInfo.copy(signUpCode = "")
                reduce {
                    copy(
                        showSignUpCodeBottomDialog = false,
                        signUpCode = ""
                    )
                }
            }

            is SignUpIntent.ChangeSignUpCode -> {
                signUpInfo = signUpInfo.copy(signUpCode = intent.signUpCode)
                reduce { copy(signUpCode = intent.signUpCode) }
            }

            SignUpIntent.ClickInputCompleteButton -> {
                signUp(
                    signUpInfo = signUpInfo,
                    reduce = reduce,
                    postSideEffect = postSideEffect,
                )
            }

            SignUpIntent.ClickNoSignUpCodeButton -> {
                signUp(
                    signUpInfo = signUpInfo.copy(signUpCode = ""),
                    reduce = reduce,
                    postSideEffect = postSideEffect,
                )
            }

            SignUpIntent.ClickPendingButton -> {
                postSideEffect(SignUpSideEffect.OpenWebBrowser(link = inquiryLink))
            }
        }
    }

    private fun signUp(
        signUpInfo: SignUpInfo,
        reduce: (SignUpState.() -> SignUpState) -> Unit,
        postSideEffect: (SignUpSideEffect) -> Unit,
    ) = viewModelScope.launch {
        postSideEffect(SignUpSideEffect.ClearFocus) // FIXME: TextField가 Focus를 가진 상태로 BottomSheet가 사라지면, 시스템 뒤로가기 이벤트가 동작하지 않는 문제가 있음

        signUpUseCase(signUpInfo)
            .onSuccess { result ->
                reduce { copy(showSignUpCodeBottomDialog = false) }
                delay(500L) // FIXME: 키보드가 완전히 내려가기 전에 다음 페이지로 넘어가면, 화면이 튕기는 현상이 있음.
                when (result) {
                    SignUpResult.Complete -> reduce { copy(currentStep = SignUpStep.Complete) }
                    SignUpResult.Pending -> reduce { copy(currentStep = SignUpStep.Pending) }
                }
            }
            .onFailure {
                when (it) {
                    is SignUpCodeException -> reduce { copy(signUpErrorInputTextDescription = it.message) }
                    else -> it.record()
                }
            }
    }

    private fun getPrimaryButtonEnable(step: SignUpStep): Boolean {
        return when (step) {
            SignUpStep.Name -> signUpInfo.name.isNotBlank()
            SignUpStep.Email -> signUpInfo.isEmailVerified
            SignUpStep.Password -> signUpInfo.isAllPasswordConditionValid
            SignUpStep.Position -> signUpInfo.isActivityUnitsValid
            SignUpStep.Complete,
            SignUpStep.Pending -> true
        }
    }
}
