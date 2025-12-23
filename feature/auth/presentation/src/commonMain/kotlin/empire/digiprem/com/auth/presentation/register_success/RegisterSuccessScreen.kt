package empire.digiprem.com.auth.presentation.register_success

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.account_successfully_created
import chirp.feature.auth.presentation.generated.resources.login
import chirp.feature.auth.presentation.generated.resources.resend_verification_email
import chirp.feature.auth.presentation.generated.resources.verification_email_send_to_x
import empire.digiprem.com.core.designsystem.components.buttons.ChirpButton
import empire.digiprem.com.core.designsystem.components.buttons.ChirpButtonStyle
import empire.digiprem.com.core.designsystem.layout.ChirpAdaptativeFormLayout
import empire.digiprem.com.core.designsystem.layout.ChirpAdaptativeResultLayout
import empire.digiprem.com.core.designsystem.layout.ChirpBrandLogo
import empire.digiprem.com.core.designsystem.layout.ChirpBrandSuccessIcon
import empire.digiprem.com.core.designsystem.layout.ChirpSimpleSuccessLayout
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterSuccessRoot(viewModel: RegisterSuccessViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    RegisterSuccessScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun RegisterSuccessScreen(state: RegisterSuccessState, onAction: (RegisterSuccessAction) -> Unit) {

    ChirpAdaptativeResultLayout {
        ChirpSimpleSuccessLayout(
            title = stringResource(Res.string.account_successfully_created),
            description = stringResource(
                Res.string.verification_email_send_to_x,
                state.registeredEmail
            ),
            icon = {
                ChirpBrandSuccessIcon()
            },
            primaryButton = {
                ChirpButton(
                    text = stringResource(Res.string.login),
                    onClick = {
                        onAction(RegisterSuccessAction.OnLoginClick)
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            secondaryButton = {
                ChirpButton(
                    text = stringResource(Res.string.resend_verification_email),
                    onClick = {
                        onAction(RegisterSuccessAction.OnLoginClick)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isRegisterVerificationEmail,
                    isLoading = state.isRegisterVerificationEmail,
                    style = ChirpButtonStyle.SECONDARY,
                )
            }
        )
    }
}


@Composable
private fun RegisterSuccessPreview() {
    RegisterSuccessScreen(
        state = RegisterSuccessState(registeredEmail = "kako@gmail.com"),
        onAction = {

        }
    )
}


@Preview
@Composable
private fun RegisterSuccessLightThemePreview() {
    ChirpTheme {
        RegisterSuccessPreview()
    }
}

@Preview
@Composable
private fun RegisterSuccessDarkThemePreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        RegisterSuccessPreview()
    }
}