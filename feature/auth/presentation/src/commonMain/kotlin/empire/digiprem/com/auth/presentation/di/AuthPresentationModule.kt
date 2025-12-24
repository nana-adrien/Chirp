package empire.digiprem.com.auth.presentation.di

import empire.digiprem.com.auth.presentation.email_verification.EmailVerificationViewModel
import empire.digiprem.com.auth.presentation.register.RegisterViewModel
import empire.digiprem.com.auth.presentation.register_success.RegisterSuccessViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::RegisterSuccessViewModel)
    viewModelOf(::EmailVerificationViewModel)
}