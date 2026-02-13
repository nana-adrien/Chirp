package empire.digiprem.com.auth.presentation.forgot_password

import androidx.compose.foundation.text.input.TextFieldState
import empire.digiprem.com.core.presentation.util.UiText


data class ForgotPasswordState(
    val emailTextFieldState: TextFieldState=TextFieldState(),
    val isLoading:Boolean=false,
    val emailError:UiText?=null,
    val errorText:UiText?=null,
    val isEmailSuccessfully:Boolean=false,
    val canSubmit:Boolean=false,
)
