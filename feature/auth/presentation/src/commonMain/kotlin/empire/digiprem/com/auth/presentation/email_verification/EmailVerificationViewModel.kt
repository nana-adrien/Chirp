package empire.digiprem.com.auth.presentation.email_verification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import empire.digiprem.com.core.domain.auth.AuthService
import empire.digiprem.com.core.domain.util.onFailure
import empire.digiprem.com.core.domain.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class EmailVerificationViewModel(
    private val authService: AuthService,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var hasLoadedInitialData = false
    private val token = savedStateHandle.get<String>("token")
    private val _state = MutableStateFlow(EmailVerificationState())
    val state = _state.onStart {
        if (!hasLoadedInitialData) {

            verifyEmail()
            /** Load initial data here **/
            hasLoadedInitialData = true
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = EmailVerificationState()
    )

    // NO-OP: Actions are purely for navigation
    fun onAction(event: EmailVerificationAction) = Unit
    private fun verifyEmail() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isVerifying = true
                )
            }
            authService.verifyEmail(token?:"Invalid token")
                .onSuccess{
                    _state.update {
                        it.copy(
                            isVerifying = false,
                            isVerified = true
                        )
                    }
                }
                .onFailure{error->
                    _state.update {
                        it.copy(
                            isVerifying = false,
                            isVerified = false
                        )
                    }
                }

        }
    }

}