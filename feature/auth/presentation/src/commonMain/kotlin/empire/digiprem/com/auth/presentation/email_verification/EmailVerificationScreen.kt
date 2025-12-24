package empire.digiprem.com.auth.presentation.email_verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.close
import chirp.feature.auth.presentation.generated.resources.email_verified_failed
import chirp.feature.auth.presentation.generated.resources.email_verified_failed_desc
import chirp.feature.auth.presentation.generated.resources.email_verified_successfully
import chirp.feature.auth.presentation.generated.resources.email_verified_successfully_desc
import chirp.feature.auth.presentation.generated.resources.login
import chirp.feature.auth.presentation.generated.resources.verifying_account
import empire.digiprem.com.core.designsystem.components.buttons.ChirpButton
import empire.digiprem.com.core.designsystem.components.buttons.ChirpButtonStyle
import empire.digiprem.com.core.designsystem.layout.ChirpAdaptativeResultLayout
import empire.digiprem.com.core.designsystem.layout.ChirpSuccessIcon
import empire.digiprem.com.core.designsystem.layout.ChirpFailureIcon
import empire.digiprem.com.core.designsystem.layout.ChirpSimpleResultLayout
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import empire.digiprem.com.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmailVerificationRoot(viewModel: EmailVerificationViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    EmailVerificationScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun EmailVerificationScreen(
    state: EmailVerificationState,
    onAction: (EmailVerificationAction) -> Unit
) {

    ChirpAdaptativeResultLayout {
        when {
            state.isVerifying -> VerifyingContent(Modifier.fillMaxSize())

            state.isVerified -> {
                ChirpSimpleResultLayout(
                    title = stringResource(Res.string.email_verified_successfully),
                    description = stringResource(Res.string.email_verified_successfully_desc),
                    icon = {
                        ChirpSuccessIcon()
                    },
                    primaryButton = {
                        ChirpButton(
                            text =stringResource(Res.string.login),
                            onClick = {
                                onAction(EmailVerificationAction.OnLoginClick)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                )
            }

            else -> {
                ChirpSimpleResultLayout(
                    title = stringResource(Res.string.email_verified_failed),
                    description = stringResource(Res.string.email_verified_failed_desc),
                    icon = {
                        Spacer(modifier = Modifier.height(32.dp))
                        ChirpFailureIcon(
                            Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                    },
                    primaryButton = {
                        ChirpButton(
                            text = stringResource(Res.string.close),
                            onClick = {
                                onAction(EmailVerificationAction.OnCloseClick)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            style = ChirpButtonStyle.SECONDARY
                        )
                    },

                )
            }
        }
    }
}

@Composable
private fun VerifyingContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.heightIn(min = 200.dp).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            16.dp, Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(Res.string.verifying_account),
            color = MaterialTheme.colorScheme.extended.textSecondary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun EmailVerificationPreview() {
    EmailVerificationScreen(
        state = EmailVerificationState(),
        onAction = {

        }
    )
}


@Preview
@Composable
private fun EmailVerificationLightThemePreview() {
    ChirpTheme {
        EmailVerificationPreview()
    }
}

@Preview
@Composable
private fun EmailVerificationDarkThemePreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        EmailVerificationPreview()
    }
}