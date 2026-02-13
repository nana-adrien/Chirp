package empire.digiprem.com.auth.presentation.reset_password

import androidx.compose.foundation.text.input.TextFieldState
import empire.digiprem.com.core.presentation.util.UiText


data class ResetPasswordState(
    val passwordTextFieldState: TextFieldState=TextFieldState(),
    val confirmPasswordTextFieldState: TextFieldState=TextFieldState(),
    val isLoading:Boolean=false,
    val errorText:UiText?=null,
    val isPasswordVisible:Boolean=false,
    val isResetSuccessfully:Boolean=false,
    val canSubmit:Boolean=false,
)
