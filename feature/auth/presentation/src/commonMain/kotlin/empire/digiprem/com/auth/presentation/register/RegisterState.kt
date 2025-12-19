 package empire.digiprem.com.auth.presentation.register

 import androidx.compose.foundation.text.input.TextFieldState
 import empire.digiprem.com.core.presentation.util.UiText

 data class RegisterState(
    val emailTextState: TextFieldState= TextFieldState(),
     val isEmailValid:Boolean=false,
     val emailError: UiText?=null,

    val passwordTextState: TextFieldState= TextFieldState(),
     val isPasswordVisible:Boolean=false,
     val isPasswordValid:Boolean=false,
     val passwordError: UiText?=null,

    val usernameTextState: TextFieldState= TextFieldState(),
     val isUsernameValid:Boolean=false,
     val usernameError: UiText?=null,

     val isRegistering:Boolean=false,
     val canRegister:Boolean=false,
     val registrationError: UiText?=null,

)