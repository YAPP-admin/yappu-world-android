package com.yapp.feature.signup.signup.page.email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.core.common.android.record
import com.yapp.core.ui.mvi.MviIntentStore
import com.yapp.core.ui.mvi.mviIntentStore
import com.yapp.dataapi.AuthRepository
import com.yapp.domain.runCatchingIgnoreCancelled
import com.yapp.model.exceptions.AlreadyRegisteredException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    val store: MviIntentStore<EmailState, EmailIntent, EmailSideEffect> =
        mviIntentStore(
            initialState = EmailState(),
            onIntent = ::onIntent
        )

    private var checkEmailJob: Job? = null

    private fun onIntent(
        intent: EmailIntent,
        state: EmailState,
        reduce: (EmailState.() -> EmailState) -> Unit,
        postSideEffect: (EmailSideEffect) -> Unit
    ) {
        when (intent) {
            is EmailIntent.ChangeEmail -> {
                checkEmailJob?.cancel()

                val newState = state.copy(
                    email = intent.email,
                    isEmailDuplicated = false,
                    isEmailChecking = false
                )
                reduce { newState }
                postSideEffect(EmailSideEffect.EmailChanged(intent.email))

                if (newState.email.isEmpty()) return
                if (newState.isEmailRegexFailed) return

                checkEmailJob = viewModelScope.launch {
                    reduce {
                        copy(isEmailChecking = true)
                    }
                    delay(500)

                    val checkEmailResult =
                        runCatchingIgnoreCancelled { authRepository.checkEmail(intent.email) }

                    checkEmailResult
                        .onSuccess {
                            reduce {
                                copy(
                                    isEmailDuplicated = false,
                                    isEmailChecking = false
                                )
                            }
                        }
                        .onFailure {
                            when (it) {
                                is AlreadyRegisteredException -> {
                                    reduce {
                                        copy(
                                            isEmailDuplicated = true,
                                            isEmailChecking = false
                                        )
                                    }
                                }

                                else -> {
                                    postSideEffect(EmailSideEffect.HandleException(it))
                                    it.record()
                                }
                            }
                        }

                    postSideEffect(
                        EmailSideEffect.EmailChanged(
                            email = intent.email,
                            verified = checkEmailResult.isSuccess
                        )
                    )
                }
            }
        }
    }
}
