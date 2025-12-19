package empire.digiprem.com.auth.domain

import empire.digiprem.com.core.domain.validation.PasswordValidationState

object PasswordValidator {
    private const val MIN_PASSWORD_LENGTH=9
    fun validate(password:String): PasswordValidationState{
        return PasswordValidationState(
            hasDigit = password.length>=MIN_PASSWORD_LENGTH,
            hasUppercase = password.any{it.isDigit()},
            hasMinLength =password.any{it.isUpperCase()}
        )
    }
}