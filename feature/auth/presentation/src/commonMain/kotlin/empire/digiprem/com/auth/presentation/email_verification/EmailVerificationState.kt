package empire.digiprem.com.auth.presentation.email_verification

data class EmailVerificationState(
    val isVerifying: Boolean=true,
    val isVerified:Boolean=false
)