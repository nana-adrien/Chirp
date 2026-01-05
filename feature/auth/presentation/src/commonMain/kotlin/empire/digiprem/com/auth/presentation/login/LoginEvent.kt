package empire.digiprem.com.auth.presentation.login

sealed interface LoginEvent {
    object Success: LoginEvent
}