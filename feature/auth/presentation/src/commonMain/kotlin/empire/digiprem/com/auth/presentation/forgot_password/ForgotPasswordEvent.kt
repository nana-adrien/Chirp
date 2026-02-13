package empire.digiprem.com.auth.presentation.forgot_password

import empire.digiprem.com.auth.presentation.reset_password.ResetPasswordEvent

sealed class ForgotPasswordEvent {
    object OnInitEvent : ForgotPasswordEvent()
}
