package empire.digiprem.com.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState
import empire.digiprem.com.core.presentation.util.UiText

data class LoginState(
    val emailTextFieldState: TextFieldState=TextFieldState(),
    val passwordTextFieldState: TextFieldState=TextFieldState(),
    val isPasswordVisible:Boolean=false,
    val canLogin:Boolean=false,
    val isLoggingIn:Boolean=false,
    val error: UiText?=null,
)