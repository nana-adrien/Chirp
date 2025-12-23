package empire.digiprem.com.auth.presentation.register_success

data class RegisterSuccessState(
    val registeredEmail:String="",
    val isRegisterVerificationEmail: Boolean=false
)