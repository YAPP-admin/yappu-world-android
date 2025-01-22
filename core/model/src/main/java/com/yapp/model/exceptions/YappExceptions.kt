package com.yapp.model.exceptions

enum class YappServerError(val exception: YappException) {
    USR_1001(SignUpCodeException()),
    USR_1002(AlreadyRegisteredException()),
    USR_1003(UnprocessedSignUpException()),
}

class SignUpCodeException() : YappException("잘못된 가입코드입니다.")
class AlreadyRegisteredException() : YappException("이미 가입된 이메일입니다.")
class UnprocessedSignUpException() : YappException("처리되지 않은 가입 신청이 존재하여, 추가 가입 신청이 불가합니다.")

open class YappException(message: String = "") : Exception(message) {
    private var _message: String = message

    override val message: String
        get() = _message

    fun setMessage(newMessage: String) {
        _message = newMessage
    }
}


