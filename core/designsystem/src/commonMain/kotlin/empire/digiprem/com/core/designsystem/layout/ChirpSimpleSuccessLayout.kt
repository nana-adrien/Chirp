package empire.digiprem.com.core.designsystem.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import empire.digiprem.com.core.designsystem.components.buttons.ChirpButton
import empire.digiprem.com.core.designsystem.components.buttons.ChirpButtonStyle
import empire.digiprem.com.core.designsystem.theme.ChirpTheme
import empire.digiprem.com.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
 fun ChirpSimpleSuccessLayout(
    title: String,
    description: String,
    icon: @Composable () -> Unit,
    primaryButton: @Composable () -> Unit,
    secondaryButton: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        icon()
        Column(
            modifier = Modifier.fillMaxWidth().offset(y = -(25).dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.extended.textPrimary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.extended.textSecondary,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        primaryButton()
        if (secondaryButton != null) {
            Spacer(modifier = Modifier.height(8.dp))
            secondaryButton()
        }
        Spacer(modifier = Modifier.height(8.dp))

    }

}


@Preview
@Composable
private fun ChirpSimpleSuccessLayoutLightThemePreview() {
    ChirpTheme {
        ChirpSimpleSuccessLayout(
            title = "Hello word",
            description = "Test description",
            icon = {
                ChirpBrandSuccessIcon()
            },
            primaryButton = {
                ChirpButton(
                    text = "Log in",
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            secondaryButton = {
                ChirpButton(
                    text = "Resend verification email",
                    style = ChirpButtonStyle.SECONDARY,
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}


@Preview
@Composable
private fun ChirpSimpleSuccessLayoutDarkThemePreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChirpSimpleSuccessLayout(
            title = "Hello word",
            description = "Test description",
            icon = {
                ChirpBrandSuccessIcon()
            },
            primaryButton = {
                ChirpButton(
                    text = "Log in",
                    onClick = {

                    }
                )
            },
            secondaryButton = {
                ChirpButton(
                    text = "Resend verification email",
                    style = ChirpButtonStyle.SECONDARY,
                    onClick = {

                    }
                )
            }
        )
    }
}