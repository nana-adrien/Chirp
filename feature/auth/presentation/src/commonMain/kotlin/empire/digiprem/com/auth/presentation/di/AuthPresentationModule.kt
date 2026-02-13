package empire.digiprem.com.auth.presentation.di

import empire.digiprem.com.auth.presentation.email_verification.EmailVerificationViewModel
import empire.digiprem.com.auth.presentation.login.LoginViewModel
import empire.digiprem.com.auth.presentation.register.RegisterViewModel
import empire.digiprem.com.auth.presentation.register_success.RegisterSuccessViewModel
import empire.digiprem.com.auth.presentation.reset_password.ResetPasswordViewModel
import empire.digiprem.com.auth.presentation.forgot_password.ForgotPasswordViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::RegisterSuccessViewModel)
    viewModelOf(::EmailVerificationViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::ResetPasswordViewModel)
}