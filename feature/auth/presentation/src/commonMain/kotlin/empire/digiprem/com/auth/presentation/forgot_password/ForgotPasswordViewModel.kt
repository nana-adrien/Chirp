package empire.digiprem.com.auth.presentation.forgot_password

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import empire.digiprem.com.auth.domain.EmailValidator
import empire.digiprem.com.auth.presentation.reset_password.ResetPasswordState
import empire.digiprem.com.core.domain.auth.AuthService
import empire.digiprem.com.core.domain.util.onFailure
import empire.digiprem.com.core.domain.util.onSuccess
import empire.digiprem.com.core.presentation.error.toUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val authService: AuthService,
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ForgotPasswordState())
    val state = _state.onStart {
        if (!hasLoadedInitialData) {
            observeValidationState()
            hasLoadedInitialData = true
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ForgotPasswordState()
    )

    private val isEmailValidFlow = snapshotFlow { state.value.emailTextFieldState.text.toString() }
        .map { email -> EmailValidator.validate(email) }.distinctUntilChanged()

    // NO-OP: Actions are purely for navigation
    fun onAction(action: ForgotPasswordAction) {
        when (action) {
            ForgotPasswordAction.OnSubmitClick -> submitForgotPasswordRequest()
        }
    }

    private fun observeValidationState() {
        isEmailValidFlow.onEach { isEmailValid ->
            _state.update {
                it.copy(
                    canSubmit = isEmailValid
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun submitForgotPasswordRequest() {
        if (state.value.isLoading || !state.value.canSubmit) {
            return
        }
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isEmailSuccessfully = false,
                    errorText = null
                )
            }
            val email = state.value.emailTextFieldState.text.toString()
            authService.forgotPassword(email = email)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isEmailSuccessfully = true,
                                    errorText = null
                        )
                    }
                }
                .onFailure {error->
                    _state.update {
                        it.copy(
                            errorText = error.toUiText(),
                            isLoading = false
                        )
                    }
                }
        }
    }
}
