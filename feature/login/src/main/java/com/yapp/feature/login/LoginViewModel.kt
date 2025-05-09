package com.yapp.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.OperationsRepository
import com.yapp.domain.LoginUseCase
import com.yapp.model.Regex
import com.yapp.model.exceptions.InvalidRequestArgument
import com.yapp.model.exceptions.LoginException
import com.yapp.model.exceptions.RecentSignUpRejectedException
import com.yapp.model.exceptions.SignUpPendingException
import com.yapp.model.exceptions.UserNotFoundForEmailException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val operationsRepository: OperationsRepository,
) : ViewModel() {
    private var privacyPolicyLink: String? = null
    private var termsLink: String? = null

    val store: MviIntentStore<LoginState, LoginIntent, LoginSideEffect> =
        mviIntentStore(
            initialState = LoginState(),
            onIntent = ::onIntent
        )

    private fun onIntent(
        intent: LoginIntent,
        state: LoginState,
        reduce: (LoginState.() -> LoginState) -> Unit,
        postSideEffect: (LoginSideEffect) -> Unit,
    ) {
        when (intent) {
            is LoginIntent.ClickLoginButton -> login(
                state.email,
                state.password,
                reduce,
                postSideEffect
            )

            is LoginIntent.ClickSignUpButton -> reduce { copy(showAgreementDialog = true) }
            is LoginIntent.ChangeEmail -> reduce {
                copy(
                    isLoginEnabled = true,
                    emailErrorDescription = null,
                    email = intent.email
                )
            }

            is LoginIntent.ChangePassword -> reduce {
                copy(
                    isLoginEnabled = true,
                    passwordErrorDescription = null,
                    password = intent.password
                )
            }

            is LoginIntent.CloseAgreementDialog -> reduce {
                copy(
                    showAgreementDialog = false,
                    personalPolicy = false,
                    terms = false,
                )
            }

            is LoginIntent.CheckTerms -> reduce { copy(terms = intent.checked) }
            is LoginIntent.CheckPersonalPolicy -> reduce { copy(personalPolicy = intent.checked) }
            is LoginIntent.ClickNextButton -> {
                reduce {
                    copy(
                        showAgreementDialog = false,
                        personalPolicy = false,
                        terms = false,
                    )
                }
                postSideEffect(LoginSideEffect.NavigateToSignUp)
            }

            LoginIntent.ClickTerms -> {
                viewModelScope.launch {
                    updateUrl()
                    termsLink?.let {
                        postSideEffect(LoginSideEffect.OpenWebBrowser(it))
                    } ?: run {
                        postSideEffect(LoginSideEffect.ShowUrlLoadFailToast)
                    }
                }
            }
            LoginIntent.ClickPersonalPolicy -> {
                viewModelScope.launch {
                    updateUrl()
                    privacyPolicyLink?.let {
                        postSideEffect(LoginSideEffect.OpenWebBrowser(it))
                    } ?: run {
                        postSideEffect(LoginSideEffect.ShowUrlLoadFailToast)
                    }
                }
            }

            LoginIntent.EnterLoginScreen -> {
                viewModelScope.launch {
                    updateUrl()
                }
            }
        }
    }

    private fun login(
        email: String,
        password: String,
        reduce: (LoginState.() -> LoginState) -> Unit,
        postSideEffect: (LoginSideEffect) -> Unit,
    ) {
        if (!email.matches(Regex.email)) {
            reduce {
                copy(
                    isLoginEnabled = true,
                    emailErrorDescription = "입력하신 이메일을 확인해주세요.",
                    passwordErrorDescription = null
                )
            }
            return
        }
        viewModelScope.launch {
            loginUseCase(email, password)
                .onSuccess {
                    postSideEffect(LoginSideEffect.NavigateToHome)
                }
                .onFailure {
                    val errorMessage = it.message ?: ""
                    reduce { copy(isLoginEnabled = true) }
                    when (it) {
                        is InvalidRequestArgument -> {
                            reduce {
                                copy(
                                    emailErrorDescription = null,
                                    passwordErrorDescription = "비밀번호가 달라요. 입력하신 비밀번호를 확인해주세요."
                                )
                            }
                        }
                        is UserNotFoundForEmailException, is LoginException -> {
                            reduce {
                                copy(
                                    emailErrorDescription = errorMessage,
                                )
                            }
                        }
                        is SignUpPendingException -> {
                            postSideEffect(LoginSideEffect.NavigateToSignUpPending)
                        }
                        is RecentSignUpRejectedException -> {
                            postSideEffect(LoginSideEffect.NavigateToSignUpReject)
                        }
                        else -> {
                            postSideEffect(LoginSideEffect.HandleException(it))
                        }
                    }
                }
        }
    }

    private suspend fun updateUrl() = coroutineScope {
        val privacyPolicyDeferred = async {
            if (privacyPolicyLink == null) {
                runCatching { operationsRepository.getPrivacyPolicyLink() }
            } else {
                Result.success(privacyPolicyLink)
            }
        }
        val termsDeferred = async {
            if (termsLink == null) {
                runCatching { operationsRepository.getTermsOfServiceLink() }
            } else {
                Result.success(termsLink)
            }
        }

        privacyPolicyLink = privacyPolicyDeferred.await().getOrNull()
        termsLink = termsDeferred.await().getOrNull()
    }
}

