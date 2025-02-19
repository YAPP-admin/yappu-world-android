package com.yapp.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.domain.LoginUseCase
import com.yapp.model.Regex
import com.yapp.model.exceptions.InvalidRequestArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
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

            LoginIntent.ClickTerms -> postSideEffect(LoginSideEffect.ShowTerms)
            LoginIntent.ClickPersonalPolicy -> postSideEffect(LoginSideEffect.ShowPersonalPolicy)
        }
    }

    private fun login(
        email: String,
        password: String,
        reduce: (LoginState.() -> LoginState) -> Unit,
        postSideEffect: (LoginSideEffect) -> Unit,
    ) {
        if (!email.matches(Regex.email)){
            reduce {
                copy(
                    isLoginEnabled = false,
                    emailErrorDescription = "입력하신 이메일을 확인해주세요.",
                    passwordErrorDescription = null
                )
            }
        }else{
            viewModelScope.launch {
                loginUseCase(email, password)
                    .onSuccess {
                        postSideEffect(LoginSideEffect.NavigateToHome)
                    }
                    .onFailure {
                        val errorMessage = it.message ?: ""
                        reduce{copy(isLoginEnabled = false)}
                        when (it) {
                            is InvalidRequestArgument -> {
                                reduce {
                                    copy(
                                        emailErrorDescription = null,
                                        passwordErrorDescription = "비밀번호가 달라요. 입력하신 비밀번호를 확인해주세요."
                                    )
                                }
                            }
                            else -> {
                                postSideEffect(LoginSideEffect.ShowToast(errorMessage))
                            }
                        }
                    }
            }

        }
    }
}

