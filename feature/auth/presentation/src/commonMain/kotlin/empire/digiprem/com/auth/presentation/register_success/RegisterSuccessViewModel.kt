package empire.digiprem.com.auth.presentation.register_success

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import empire.digiprem.com.auth.presentation.register.RegisterEvent
import empire.digiprem.com.core.domain.auth.AuthService
import empire.digiprem.com.core.domain.util.onFailure
import empire.digiprem.com.core.domain.util.onSuccess
import empire.digiprem.com.core.presentation.error.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class RegisterSuccessViewModel(
   private val authService: AuthService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val _eventChannel = Channel<RegisterSuccessEvent>()
    val event = _eventChannel.receiveAsFlow()
    private var email=savedStateHandle.get<String>("email")?:throw IllegalStateException("No email passed to register success screen")
    private val _state = MutableStateFlow(RegisterSuccessState(registeredEmail = email))
    val state = _state.onStart {
        if (!hasLoadedInitialData) {

            /** Load initial data here **/
            hasLoadedInitialData = true
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = RegisterSuccessState()
    )

    fun onAction(event: RegisterSuccessAction) {
        when (event) {
            is RegisterSuccessAction.OnResendVerificationEmailClick->resendVerification()
            is RegisterSuccessAction.OnLoginClick ->Unit
            else -> {

            }
        }
    }

    private fun resendVerification() {
        if (state.value.isRegisterVerificationEmail){
            _state.update {
                it.copy(
                    isRegisterVerificationEmail = true
                )
            }
        }
        val email=state.value.registeredEmail
        viewModelScope.launch {
            authService.resendVerificationEmail(email)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isRegisterVerificationEmail = false
                        )
                    }
                    _eventChannel.send(RegisterSuccessEvent.ResentVerificationEmailSuccess)
                }
                .onFailure {error->
                    _state.update {
                        it.copy(
                            isRegisterVerificationEmail = false,
                            resendVerificationError = error.toUiText()
                        )
                    }
                }
        }
    }

}