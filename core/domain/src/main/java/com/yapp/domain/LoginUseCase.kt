package com.yapp.domain

import com.yapp.dataapi.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email : String, password : String) = runCatchingIgnoreCancelled {
        loginRepository.login(email,password)
    }
}