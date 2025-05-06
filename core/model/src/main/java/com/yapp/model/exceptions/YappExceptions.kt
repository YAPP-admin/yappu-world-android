package com.yapp.model.exceptions

enum class YappServerError(val exception: YappException) {
    COM_0001(InternalServerException()),
    COM_0002(InvalidRequestArgument()),

    // 회원가입
    USR_1001(SignUpCodeException()),
    USR_1002(AlreadyRegisteredException()),
    USR_1003(UnprocessedSignUpException()),

    // 로그인
    USR_0002(InternalServerException()),
    USR_1101(UserNotFoundForEmailException()),
    USR_1102(SignUpPendingException()),
    USR_1103(RecentSignUpRejectedException()),
    USR_1104(LoginBlockedException()),
    USR_1105(LoginException()),

    //토큰 만료
    TKN_0001(InvalidTokenException()),
    TKN_0002(InvalidTokenException()),

    //세션
    SCH_1005(NoScheduledSessionException()),

    //출석 체크
    ATD_1001(CodeNotCorrectException()),
    TKN_0002(InvalidTokenException()),

    NOT_DEFINED(NotDefinedException());

    companion object {
        fun safeValueOf(name: String): YappServerError {
            return try {
                valueOf(name)
            } catch (e: IllegalArgumentException) {
                NOT_DEFINED
            }
        }
    }
}

class InternalServerException : YappException()
class InvalidRequestArgument : YappException()
class SignUpCodeException : YappException("잘못된 가입코드입니다.")
class AlreadyRegisteredException : YappException("이미 가입된 이메일입니다.")
class UnprocessedSignUpException : YappException("처리되지 않은 가입 신청이 존재하여, 추가 가입 신청이 불가합니다.")
class UserNotFoundForEmailException : YappException("계정 정보를 찾을 수 없습니다.")
class SignUpPendingException : YappException("회원가입 처리가 진행 중입니다.")
class RecentSignUpRejectedException : YappException("최근의 회원가입 신청은 거절되었습니다.")
class LoginBlockedException : YappException("로그인이 불가능한 회원 상태입니다.")
class LoginException : YappException("로그인에 실패했습니다. 계정 정보를 다시 확인하세요.")
class InvalidTokenException : YappException("비정상 토큰입니다")
class CodeNotCorrectException : YappException("출석 코드가 일치하지 않습니다.")
class NoScheduledSessionException : YappException("예정된 세션이 존재하지 않습니다.")
class NotDefinedException : YappException("정의되지 않은 오류입니다.")

open class YappException(message: String = "") : Exception(message) {
    private var _message: String = message

    override val message: String
        get() = _message

    fun setMessage(newMessage: String) {
        _message = newMessage
    }
}
