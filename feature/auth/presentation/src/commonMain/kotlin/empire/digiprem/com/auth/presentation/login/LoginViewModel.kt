package empire.digiprem.com.auth.presentation.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_invalid_credentials
import chirp.feature.auth.presentation.generated.resources.error_invalid_email
import empire.digiprem.com.auth.domain.EmailValidator
import empire.digiprem.com.core.domain.auth.AuthService
import empire.digiprem.com.core.domain.auth.SessionStorage
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.onFailure
import empire.digiprem.com.core.domain.util.onSuccess
import empire.digiprem.com.core.presentation.error.toUiText
import empire.digiprem.com.core.presentation.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel(
    private val authService: AuthService,
    private val sessionStorage: SessionStorage
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val _eventChannel = Channel<LoginEvent>()
    val events = _eventChannel.receiveAsFlow()
    private val _state = MutableStateFlow(LoginState())

    private val isEmailValidFlow = snapshotFlow { state.value.emailTextFieldState.text.toString() }
        .map { email -> EmailValidator.validate(email) }.distinctUntilChanged()
    private val isPasswordNotBlackFlow = snapshotFlow { state.value.passwordTextFieldState.text.toString() }
        .map { password -> password.isNotEmpty() }.distinctUntilChanged()

    val state = _state.onStart {
        if (!hasLoadedInitialData) {
            observeTextState()
            hasLoadedInitialData = true
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = LoginState()
    )

    private val isRegisteringFlow = state
        .map { it.isLoggingIn }.distinctUntilChanged()


    private fun observeTextState() {
        combine(
            isEmailValidFlow,
            isPasswordNotBlackFlow,
            isRegisteringFlow
        ) { isEmailValid, isPasswordNotBlackFlow, isRegistering ->
            _state.update {
                it.copy(
                    canLogin = isEmailValid && isPasswordNotBlackFlow && !isRegistering
                )
            }

        }.launchIn(viewModelScope)

    }

    fun onAction(event: LoginAction) {
        when (event) {
            LoginAction.OnLoginClick -> login()
            LoginAction.OnTogglePasswordVisibility -> {
                _state.update {
                    it.copy(
                        isPasswordVisible = !it.isPasswordVisible
                    )
                }
            }
            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoggingIn = true
                )
            }

            val email = state.value.emailTextFieldState.text.toString()
            val password = state.value.passwordTextFieldState.text.toString()

            authService.login(email, password)
                .onSuccess { authInfo ->
                    sessionStorage.set(authInfo)
                    _eventChannel.send(LoginEvent.Success)
                    _state.update {
                        it.copy(
                            isLoggingIn = false
                        )
                    }
                }
                .onFailure { error ->
                    val errorMessage = when (error) {
                        DataError.Remote.UNAUTHORIZED -> UiText.Resource(Res.string.error_invalid_email)
                        DataError.Remote.FORBIDDEN -> UiText.Resource(Res.string.error_invalid_credentials)
                        else -> error.toUiText()
                    }
                    _state.update {
                        it.copy(
                            error = errorMessage,
                            isLoggingIn = false
                        )
                    }
                }
        }


    }

}