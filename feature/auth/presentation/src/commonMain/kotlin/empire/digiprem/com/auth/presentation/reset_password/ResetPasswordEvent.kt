package empire.digiprem.com.auth.presentation.reset_password

import empire.digiprem.com.auth.presentation.forgot_password.ForgotPasswordEvent

sealed class ResetPasswordEvent {
    object OnInitEvent : ResetPasswordEvent()
}
