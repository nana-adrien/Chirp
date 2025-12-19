package empire.digiprem.com.auth.presentation.register

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.email_placeholder
import chirp.feature.auth.presentation.generated.resources.email
import chirp.feature.auth.presentation.generated.resources.login
import chirp.feature.auth.presentation.generated.resources.password
import chirp.feature.auth.presentation.generated.resources.password_hint
import chirp.feature.auth.presentation.generated.resources.register
import chirp.feature.auth.presentation.generated.resources.username
import chirp.feature.auth.presentation.generated.resources.username_hint
import chirp.feature.auth.presentation.generated.resources.username_placeholder
import chirp.feature.auth.presentation.generated.resources.welcome_to_chirp
import empire.digiprem.com.core.designsystem.components.buttons.ChirpButton
import empire.digiprem.com.core.designsystem.components.buttons.ChirpButtonStyle
import empire.digiprem.com.core.designsystem.components.textfields.ChirpPasswordTextField
import empire.digiprem.com.core.designsystem.components.textfields.ChirpTextField
import empire.digiprem.com.core.designsystem.layout.ChirpAdaptativeFormLayout
import empire.digiprem.com.core.designsystem.layout.ChirpBrandLogo
import empire.digiprem.com.core.designsystem.layout.ChirpSnackBarScaffold
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RegisterRoot(viewModel: RegisterViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    val snackbarHostState=remember{
        SnackbarHostState()
    }
    RegisterScreen(
        state = state,snackbarHostState,
        onAction = viewModel::onAction
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    snackbarHostState: SnackbarHostState,
    onAction: (RegisterAction) -> Unit,) {

    ChirpSnackBarScaffold(
        snackbarHostState = snackbarHostState,
    ){
        ChirpAdaptativeFormLayout(
            headerText = stringResource(Res.string.welcome_to_chirp),
            errorText = state.registrationError?.asString(),
            logo = { ChirpBrandLogo() }
        ){
            Spacer(modifier= Modifier.height(16.dp))

            ChirpTextField(
                state=state.usernameTextState,
                placeholder = stringResource(Res.string.username_placeholder),
                title = stringResource(Res.string.username),
                supportingText = state.usernameError?.asString()?: stringResource(Res.string.username_hint),
                isError = state.usernameError!=null,
                onFocusChange = {isFocused->
                    onAction(RegisterAction.OnInputTextFocusGain)
                }
            )

            Spacer(modifier= Modifier.height(16.dp))

            ChirpTextField(
                state=state.emailTextState,
                placeholder = stringResource(Res.string.email_placeholder),
                title = stringResource(Res.string.email),
                supportingText = state.emailError?.asString()?: stringResource(Res.string.email_placeholder),
                isError = state.emailError!=null,
                onFocusChange = {isFocused->
                    onAction(RegisterAction.OnInputTextFocusGain)
                }
            )

            Spacer(modifier= Modifier.height(16.dp))

            ChirpPasswordTextField(
                state=state.passwordTextState,
                placeholder = stringResource(Res.string.password),
                title = stringResource(Res.string.email),
                supportingText = state.passwordError?.asString()?: stringResource(Res.string.password_hint),
                isError = state.passwordError!=null,
                onFocusChange = {isFocused->
                    onAction(RegisterAction.OnInputTextFocusGain)
                },
                onToggleVisibilityClick = {
                    onAction(RegisterAction.OnTogglePasswordVisibilityClick)
                },
                isPasswordVisible = state.isPasswordVisible
            )


            Spacer(modifier= Modifier.height(16.dp))

            ChirpButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.register),
                onClick = {
                    onAction(RegisterAction.OnRegisterClick)
                },
                enabled=state.canRegister,
                isLoading = state.isRegistering
            )

            Spacer(modifier= Modifier.height(8.dp))
            ChirpButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.login),
                onClick = {
                    onAction(RegisterAction.OnLoginClick)
                },
                style= ChirpButtonStyle.SECONDARY
            )
        }
    }

}



@Composable
 fun RegisterScreenPreview() {
    RegisterScreen(
        state = RegisterState(),
        snackbarHostState = SnackbarHostState(),
        onAction = {

        }
    )
}

@Preview
@Composable
private fun RegisterScreenLightThemePreview() {
    ChirpTheme {
        RegisterScreenPreview()
    }
}

@Preview
@Composable
private fun RegisterScreenDarkThemePreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        RegisterScreenPreview()
    }
}