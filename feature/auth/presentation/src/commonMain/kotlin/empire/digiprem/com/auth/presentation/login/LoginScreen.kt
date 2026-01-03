package empire.digiprem.com.auth.presentation.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.email
import chirp.feature.auth.presentation.generated.resources.email_placeholder
import chirp.feature.auth.presentation.generated.resources.password
import chirp.feature.auth.presentation.generated.resources.welcome_back
import empire.digiprem.com.auth.presentation.register.RegisterAction
import empire.digiprem.com.core.designsystem.components.textfields.ChirpPasswordTextField
import empire.digiprem.com.core.designsystem.components.textfields.ChirpTextField
import empire.digiprem.com.core.designsystem.layout.ChirpAdaptativeFormLayout
import empire.digiprem.com.core.designsystem.layout.ChirpBrandLogo
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoginRoot(viewModel: LoginViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    LoginScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun LoginScreen(state: LoginState, onAction: (LoginAction) -> Unit) {

    ChirpAdaptativeFormLayout(
        headerText = stringResource(Res.string.welcome_back),
        errorText = state.error?.asString(),
        logo = {
            ChirpBrandLogo()
        },
        modifier= Modifier.fillMaxSize(),

    ) {
        ChirpTextField(
            state=state.emailTextFieldState,
            placeholder = stringResource(Res.string.email_placeholder),
            keyboardType = KeyboardType.Email,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.email)
        )
        ChirpPasswordTextField(
            state=state.passwordTextFieldState,
            placeholder = stringResource(Res.string.password),
            isPasswordVisible = state.isPasswordVisible,
            onFocusChange = {isFocused->
                //onAction(LoginAction.OnInputTextFocusGain)
            },
            onToggleVisibilityClick = {
                onAction(LoginAction.OnTogglePasswordVisibility)
            },
            modifier = Modifier.fillMaxWidth()
        )

    }
}


@Composable
private fun LoginPreview() {
    LoginScreen(
        state = LoginState(),
        onAction = {

        }
    )
}


@Preview
@Composable
private fun LoginLightThemePreview() {
    ChirpTheme {
        LoginPreview()
    }
}

@Preview
@Composable
private fun LoginDarkThemePreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        LoginPreview()
    }
}