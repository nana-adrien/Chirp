package empire.digiprem.com.auth.presentation.register_success

import empire.digiprem.com.core.presentation.util.UiText

data class RegisterSuccessState(
    val registeredEmail:String="",
    val isRegisterVerificationEmail: Boolean=false,
    val resendVerificationError: UiText?=null
)