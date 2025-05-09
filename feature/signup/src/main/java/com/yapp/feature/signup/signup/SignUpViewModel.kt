package com.yapp.feature.signup.signup

import androidx.lifecycle.SavedStateHandle
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
import com.yapp.model.exceptions.UnprocessedSignUpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val getPositionConfigsUseCase: GetPositionConfigsUseCase,
    private val operationsRepository: OperationsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var signUpInfo = SignUpInfo()
    private var inquiryLink: String? = null

    companion object {
        private const val STEP_ID_KEY = "currentStep"
    }

    private val step: String =
        requireNotNull(savedStateHandle.get<String>(STEP_ID_KEY)) { "Name" }


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
            is SignUpIntent.EnterScreen -> {
                reduce { copy(currentStep = SignUpStep.from(step)) }
                getPositionConfigsUseCase()
                    .onEach {
                        reduce { copy(positions = it) }
                    }
                    .catch {
                        it.record()
                    }
                    .launchIn(viewModelScope)

                viewModelScope.launch {
                    updateUrl()
                }
            }

            SignUpIntent.BackPressed,
            SignUpIntent.ClickBackButton -> {
                val previousStep = when (state.currentStep) {
                    SignUpStep.Position -> SignUpStep.Password
                    SignUpStep.Password -> SignUpStep.Email
                    SignUpStep.Email -> SignUpStep.Name
                    SignUpStep.Pending,
                    SignUpStep.Reject,
                    SignUpStep.Name -> {
                        postSideEffect(SignUpSideEffect.NavigateBack)
                        return
                    }

                    SignUpStep.Complete -> {
                        postSideEffect(SignUpSideEffect.NavigateHome)
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
                    SignUpStep.Reject,
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
                signUpInfo =
                    signUpInfo.copy(email = intent.email, isEmailVerified = intent.verified)
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
                viewModelScope.launch {
                    updateUrl()
                    inquiryLink?.let { inquiryLink ->
                        postSideEffect(SignUpSideEffect.OpenWebBrowser(link = inquiryLink))
                    } ?: run {
                        postSideEffect(SignUpSideEffect.ShowUrlLoadFailToast)
                    }
                }
            }

            is SignUpIntent.HandleException -> postSideEffect(SignUpSideEffect.HandleException(intent.exception))
        }
    }

    private fun signUp(
        signUpInfo: SignUpInfo,
        reduce: (SignUpState.() -> SignUpState) -> Unit,
        postSideEffect: (SignUpSideEffect) -> Unit,
    ) = viewModelScope.launch {
        postSideEffect(SignUpSideEffect.ClearFocus) // FIXME: TextField가 Focus를 가진 상태로 BottomSheet가 사라지면, 시스템 뒤로가기 이벤트가 동작하지 않는 문제가 있음
        reduce { copy(isSignUpRequesting = true) }

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
                    is UnprocessedSignUpException -> reduce {
                        copy(
                            currentStep = SignUpStep.Pending,
                            showSignUpCodeBottomDialog = false
                        )
                    }

                    else -> {
                        postSideEffect(SignUpSideEffect.HandleException(it))
                        it.record()
                    }
                }
            }
        reduce { copy(isSignUpRequesting = false) }
    }

    private fun getPrimaryButtonEnable(step: SignUpStep): Boolean {
        return when (step) {
            SignUpStep.Name -> signUpInfo.name.isNotBlank()
            SignUpStep.Email -> signUpInfo.isEmailVerified
            SignUpStep.Password -> signUpInfo.isAllPasswordConditionValid
            SignUpStep.Position -> signUpInfo.isActivityUnitsValid
            SignUpStep.Complete,
            SignUpStep.Reject,
            SignUpStep.Pending -> true
        }
    }

    private suspend fun updateUrl() = coroutineScope {
        val inquiryDeferred = async {
            if (inquiryLink == null) {
                runCatching { operationsRepository.getUsageInquiryLink() }
            } else {
                Result.success(inquiryLink)
            }
        }

        inquiryLink = inquiryDeferred.await().getOrNull()
    }
}
