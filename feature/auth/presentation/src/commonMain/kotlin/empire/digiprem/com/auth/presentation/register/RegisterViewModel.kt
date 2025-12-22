package empire.digiprem.com.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_account_exists
import chirp.feature.auth.presentation.generated.resources.error_invalid_email
import chirp.feature.auth.presentation.generated.resources.error_invalid_password
import empire.digiprem.com.auth.domain.EmailValidator
import empire.digiprem.com.auth.domain.PasswordValidator
import empire.digiprem.com.core.domain.auth.AuthService
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.onFailure
import empire.digiprem.com.core.domain.util.onSuccess
import empire.digiprem.com.core.presentation.error.toUiText
import empire.digiprem.com.core.presentation.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class RegisterViewModel(
    private val authService: AuthService
) : ViewModel() {

    private val _eventChannel=Channel<RegisterEvent>()
    val event=_eventChannel.receiveAsFlow()
    private var hasLoadedInitialData = false
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.onStart {
        if (!hasLoadedInitialData) {

            /** Load initial data here **/
            hasLoadedInitialData = true
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = RegisterState()
    )

    fun onAction(event: RegisterAction) {
        when (event) {
            is RegisterAction.OnLoginClick -> validateFormInputs()
            is RegisterAction.OnInputTextFocusGain -> clearAllTextFieldErrors()
            is RegisterAction.OnRegisterClick -> register()
            is RegisterAction.OnTogglePasswordVisibilityClick -> {
                _state.update {
                    it.copy(
                        isPasswordVisible = !it.isPasswordVisible
                    )
                }
            }
            else -> Unit
        }
    }

    private fun register() {
        if (!validateFormInputs()) {
            return
        }
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRegistering = true
                )
            }
            val email = state.value.emailTextState.text.toString()
            val username = state.value.usernameTextState.text.toString()
            val password = state.value.passwordTextState.text.toString()

            authService.register(email, username, password)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isRegistering = true
                        )
                    }

                }.onFailure { error ->
                    when(error){
                        DataError.Remote.CONFLICT -> UiText.Resource(Res.string.error_account_exists)
                        else -> error.toUiText()
                    }

                    _state.update {
                        it.copy(
                            isRegistering = false
                        )
                    }

                }

        }
    }

    private fun clearAllTextFieldErrors() {
        _state.update {
            it.copy(
                emailError = null,
                usernameError = null,
                passwordError = null,
                registrationError = null
            )
        }
    }

    private fun validateFormInputs(): Boolean {
        clearAllTextFieldErrors()
        val currentState = state.value
        val email = currentState.emailTextState.text.toString()
        val password = currentState.passwordTextState.text.toString()
        val username = currentState.usernameTextState.text.toString()

        val isEmailValid = EmailValidator.validate(email)
        val passwordValidationState = PasswordValidator.validate(password)
        val isUsernameValid = username.length in 3..20

        val emailError = if (!isEmailValid) {
            UiText.Resource(Res.string.error_invalid_email)
        } else null

        val usernameError = if (!isEmailValid) {
            UiText.Resource(Res.string.error_account_exists)
        } else null

        val passwordError = if (!isEmailValid) {
            UiText.Resource(Res.string.error_invalid_password)
        } else null

        _state.update {
            it.copy(
                emailError = emailError,
                usernameError = usernameError,
                passwordError = passwordError
            )
        }
        return isUsernameValid && isEmailValid && passwordValidationState.isValidPassword
    }

}