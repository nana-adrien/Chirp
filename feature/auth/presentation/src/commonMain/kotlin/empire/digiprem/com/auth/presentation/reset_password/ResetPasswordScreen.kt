package empire.digiprem.com.auth.presentation.reset_password

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_reset_password_token_invalid
import chirp.feature.auth.presentation.generated.resources.forgot_password_email_send_successfully
import chirp.feature.auth.presentation.generated.resources.password
import chirp.feature.auth.presentation.generated.resources.password_hint
import chirp.feature.auth.presentation.generated.resources.reset_password_successfully
import chirp.feature.auth.presentation.generated.resources.set_new_password
import chirp.feature.auth.presentation.generated.resources.submit
import empire.digiprem.com.core.designsystem.components.buttons.ChirpButton
import empire.digiprem.com.core.designsystem.components.textfields.ChirpPasswordTextField
import empire.digiprem.com.core.designsystem.layout.ChirpAdaptativeFormLayout
import empire.digiprem.com.core.designsystem.layout.ChirpBrandLogo
import empire.digiprem.com.core.designsystem.layout.ChirpSnackBarScaffold
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import empire.digiprem.com.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ResetPasswordRoot(
    viewModel: ResetPasswordViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ResetPasswordScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ResetPasswordScreen(
    state: ResetPasswordState,
    onAction: (ResetPasswordAction) -> Unit
) {
    ChirpSnackBarScaffold{
        ChirpAdaptativeFormLayout(
            headerText = stringResource(Res.string.set_new_password),
            errorText =state.errorText?.asString() ,
            logo = {
                ChirpBrandLogo()
            },
            modifier = Modifier,
        )
        {
            ChirpPasswordTextField(
                state=state.passwordTextFieldState,
                modifier = Modifier.fillMaxWidth(),
                placeholder = stringResource(Res.string.password),
                title = stringResource(Res.string.password),
                // isError = state.emailError!=null,
                supportingText = stringResource(Res.string.password_hint),
                isPasswordVisible = state.isPasswordVisible,
                onToggleVisibilityClick = {
                    onAction(ResetPasswordAction.OnTogglePasswordVisibilityClick)
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            ChirpButton(
                text = stringResource(Res.string.submit),
                onClick = {
                    onAction(ResetPasswordAction.OnSubmitClick)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled =!state.isLoading && state.canSubmit,
                isLoading = state.isLoading
            )
            if (state.isResetSuccessfully){
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(Res.string.reset_password_successfully),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.extended.onSuccess,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }


        }
    }
}

@Composable
private fun ResetPasswordPreview() {
    ResetPasswordScreen(
        state = ResetPasswordState(),
        onAction = {

        }
    )
}


@Preview
@Composable
private fun ResetPasswordLightThemePreview() {
    ChirpTheme {
        ResetPasswordPreview()
    }
}

@Preview
@Composable
private fun ResetPasswordDarkThemePreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        ResetPasswordPreview()
    }
}
