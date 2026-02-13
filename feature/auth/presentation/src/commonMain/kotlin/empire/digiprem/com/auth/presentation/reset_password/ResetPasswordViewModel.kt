package empire.digiprem.com.auth.presentation.reset_password

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_reset_password_token_invalid
import chirp.feature.auth.presentation.generated.resources.error_same_password
import empire.digiprem.com.auth.domain.EmailValidator
import empire.digiprem.com.auth.domain.PasswordValidator
import empire.digiprem.com.core.domain.auth.AuthService
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.onFailure
import empire.digiprem.com.core.domain.util.onSuccess
import empire.digiprem.com.core.presentation.error.toUiText
import empire.digiprem.com.core.presentation.util.UiText
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

class ResetPasswordViewModel(
    private val authService: AuthService,
    private val savedStateHandle:SavedStateHandle
) : ViewModel() {
    private var hasLoadedInitialData = false
private val token=savedStateHandle.get<String>("token")?: throw IllegalStateException("NO password reset token")
    private val isPasswordValidFlow =  snapshotFlow { state.value.passwordTextFieldState.text.toString() }
        .map { password -> PasswordValidator.validate(password).isValidPassword }
        .distinctUntilChanged()
    private val _state = MutableStateFlow(ResetPasswordState())
    val state = _state.onStart {
        if (!hasLoadedInitialData) {
            observeValidationState()
            /** Load initial data here **/
            hasLoadedInitialData = true
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ResetPasswordState()
    )

    // NO-OP: Actions are purely for navigation
    fun onAction(action: ResetPasswordAction) {
        when (action) {
            ResetPasswordAction.OnSubmitClick -> submitResetPasswordRequest()
            ResetPasswordAction.OnTogglePasswordVisibilityClick -> {
                _state.update {
                    it.copy(
                        isPasswordVisible = !it.isPasswordVisible
                    )
                }
            }
        }
    }

    private fun observeValidationState() {
        isPasswordValidFlow.onEach { isPasswordValid ->
            _state.update {
                it.copy(
                    canSubmit = isPasswordValid
                )
            }
        }.launchIn(viewModelScope)

    }

    private fun submitResetPasswordRequest() {
        if (state.value.isLoading || !state.value.canSubmit) {
            return
        }
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isResetSuccessfully = false,
                    errorText = null
                )
            }
            val newPassword = state.value.passwordTextFieldState.text.toString()
            authService.resetPassword(newPassword = newPassword,token=token)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isResetSuccessfully = true,
                            errorText = null
                        )
                    }
                }
                .onFailure {error->
                    val errorText=when(error){
                         DataError.Remote.UNAUTHORIZED -> UiText.Resource(Res.string.error_reset_password_token_invalid)
                         DataError.Remote.CONFLICT -> UiText.Resource(Res.string.error_same_password)
                        else-> error.toUiText()
                    }
                    _state.update {
                        it.copy(
                            errorText = errorText,
                            isLoading = false
                        )
                    }
                }
        }
    }
}
