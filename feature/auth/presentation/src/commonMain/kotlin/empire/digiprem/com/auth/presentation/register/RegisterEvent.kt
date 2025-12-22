package empire.digiprem.com.auth.presentation.register

sealed interface RegisterEvent {
    data class Success(val data: String): RegisterEvent
}